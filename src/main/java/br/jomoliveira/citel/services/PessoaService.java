package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.PessoaDTO;
import br.jomoliveira.citel.models.Doacao;
import br.jomoliveira.citel.models.Endereco;
import br.jomoliveira.citel.models.Pessoa;
import br.jomoliveira.citel.repositories.DoacaoRepository;
import br.jomoliveira.citel.repositories.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    private final PessoaRepository repository;
    private final TelefoneService telefoneService;
    private final EnderecoService enderecoService;
    private final TipoSanguineoService tipoSanguineoService;
    private final ImportacaoService importacaoService;
    private final DoacaoRepository doacaoRepository;

    private static final Integer IMC_OBESO = 30;
    private static final Integer IDADE_MINIMA = 16;
    private static final Integer IDADE_MAXIMA = 69;
    private static final Integer PESO_MINIMO_PARA_DOACAO = 50;

    public PessoaService(PessoaRepository repository, TelefoneService telefoneService, EnderecoService enderecoService, TipoSanguineoService tipoSanguineoService, ImportacaoService importacaoService, DoacaoRepository doacaoRepository) {
        this.repository = repository;
        this.telefoneService = telefoneService;
        this.enderecoService = enderecoService;
        this.tipoSanguineoService = tipoSanguineoService;
        this.importacaoService = importacaoService;
        this.doacaoRepository = doacaoRepository;
    }

    private List<PessoaDTO> _mapearJsonParaPessoaDTO(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(json, PessoaDTO[].class));
    }

    public void salvar(String json, Long idImportacao) throws IOException {
        var listPessoaDTO = _mapearJsonParaPessoaDTO(json);
        listPessoaDTO.forEach(pessoaDTO -> {
            salvar(converterParaEntidade(pessoaDTO, idImportacao));
        });
    }

    public void salvar(Pessoa pessoa) {
        repository.save(pessoa);
    }

    public Pessoa converterParaEntidade(PessoaDTO pessoaDTO, Long idImportacao) {
        return new Pessoa(
                pessoaDTO.nome(),
                pessoaDTO.cpf(),
                pessoaDTO.rg(),
                _converterStringParaData(pessoaDTO.data_nasc()),
                pessoaDTO.sexo(),
                pessoaDTO.mae(),
                pessoaDTO.pai(),
                pessoaDTO.altura(),
                pessoaDTO.peso(),
                tipoSanguineoService.findTipoSanguineo(pessoaDTO.tipo_sanguineo()),
                enderecoService.converterParaEntidade(pessoaDTO),
                telefoneService.converterParaEntidade(pessoaDTO),
                importacaoService.findById(idImportacao)
        );
    }

    private LocalDate _converterStringParaData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formatter);
    }

    public Map<String, Long> pessoaPorEstado(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .map(Pessoa::getEndereco)
                .collect(Collectors.groupingBy(Endereco::getEstado, Collectors.counting()));
    }

    public Map<String, Long> calcularObesidadePorSexo(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .filter(pessoa -> pessoa.getImc() > IMC_OBESO)
                .map(Pessoa::getSexo)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));
    }

    public Map<String, Double> mediaIdadeParaCadaTipoSanguineo(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .collect(Collectors.groupingBy(
                        pessoa -> pessoa.getTipoSanguineo().getSorotipagem(),
                        Collectors.averagingDouble(Pessoa::getIdade)
                ));
    }

    public Map<String, Double> calcularIMCMedioPorFaixaDeIdade(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .collect(Collectors.groupingBy(
                        pessoa -> {
                            int idade = pessoa.getIdade();
                            int faixaDeIdade = idade - (idade % 10);
                            return faixaDeIdade + "-" + (faixaDeIdade + 9);
                        },
                        Collectors.averagingDouble(Pessoa::getImc)
                ));
    }

    public Map<String, Double> calcularQuantidadeDoadoresPorTipoSanguineo(Long idImportacao) {
        List<Pessoa> pessoasDoadoras = repository.buscarTodos(idImportacao).stream()
                .filter(pessoa -> pessoa.getIdade() >= IDADE_MINIMA && pessoa.getIdade() <= IDADE_MAXIMA && pessoa.getPeso() > PESO_MINIMO_PARA_DOACAO)
                .toList();

        List<Doacao> regrasDoacao = doacaoRepository.findAll();

        Map<String, Double> quantidadeDoadoresPorTipoSanguineo = new HashMap<>();

        for (Pessoa pessoa : pessoasDoadoras) {
            String tipoSanguineoDoador = pessoa.getTipoSanguineo().getSorotipagem();

            for (Doacao regra : regrasDoacao) {
                if (regra.getTipoSanguineoDoador().getSorotipagem().equals(tipoSanguineoDoador)) {
                    String tipoSanguineoReceptor = regra.getTipoSanguineoReceptor().getSorotipagem();
                    quantidadeDoadoresPorTipoSanguineo.put(tipoSanguineoReceptor,
                            quantidadeDoadoresPorTipoSanguineo.getOrDefault(tipoSanguineoReceptor, 0.0) + 1);
                }
            }
        }

        return quantidadeDoadoresPorTipoSanguineo;
    }
}
