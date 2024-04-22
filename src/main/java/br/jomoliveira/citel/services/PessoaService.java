package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.*;
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
import java.util.*;
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

    public List<CandidatosPorEstadoDTO> pessoaPorEstado(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .map(Pessoa::getEndereco)
                .collect(Collectors.groupingBy(Endereco::getEstado, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new CandidatosPorEstadoDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ObesidadePorSexoDTO> calcularObesidadePorSexo(Long idImportacao) {
        List<Pessoa> populacaoTotal = repository.buscarTodos(idImportacao);

        Map<String, Long> populacaoObesa = populacaoTotal.stream()
                .filter(pessoa -> pessoa.getPeso() / (pessoa.getAltura() * pessoa.getAltura()) > IMC_OBESO)
                .collect(Collectors.groupingBy(Pessoa::getSexo, Collectors.counting()));

        Map<String, Long> populacaoTotalPorSexo = populacaoTotal.stream()
                .collect(Collectors.groupingBy(Pessoa::getSexo, Collectors.counting()));

        List<ObesidadePorSexoDTO> obesidadePorSexoDTOs = new ArrayList<>();

        for (Map.Entry<String, Long> entry : populacaoTotalPorSexo.entrySet()) {
            String sexo = entry.getKey();
            Long populacaoTotalMap = entry.getValue();
            Long populacaoObesaMap = populacaoObesa.getOrDefault(sexo, 0L);
            double percentual = (double) populacaoObesaMap / populacaoTotalMap * 100;

            ObesidadePorSexoDTO dto = new ObesidadePorSexoDTO(sexo, populacaoTotalMap, populacaoObesaMap, percentual);
            obesidadePorSexoDTOs.add(dto);
        }

        return obesidadePorSexoDTOs;
    }

    public List<MediaParaCadaTipoSanguineoDTO> mediaIdadeParaCadaTipoSanguineo(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .collect(Collectors.groupingBy(
                        pessoa -> pessoa.getTipoSanguineo().getSorotipagem(),
                        Collectors.averagingDouble(Pessoa::getIdade)
                )).entrySet().stream()
                .map(entry -> new MediaParaCadaTipoSanguineoDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ImcMedioPorFaixaEtariaDTO> calcularIMCMedioPorFaixaDeIdade(Long idImportacao) {
        return repository.buscarTodos(idImportacao).stream()
                .collect(Collectors.groupingBy(
                        pessoa -> {
                            int idade = pessoa.getIdade();
                            int faixaDeIdade = idade - (idade % 10);
                            return faixaDeIdade + "-" + (faixaDeIdade + 9);
                        },
                        Collectors.averagingDouble(Pessoa::getImc)
                )).entrySet().stream()
                .map(entry -> new ImcMedioPorFaixaEtariaDTO(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    public List<DoadoresPorReceptorDTO> calcularQuantidadeDoadoresPorTipoSanguineo(Long idImportacao) {
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

        return quantidadeDoadoresPorTipoSanguineo.entrySet()
                .stream()
                .map(entry -> new DoadoresPorReceptorDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
