package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.models.Pessoa;
import br.jomoliveira.citel.repositories.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PessoaService {
    private final PessoaRepository repository;
    private final TelefoneService telefoneService;
    private final EnderecoService enderecoService;
    private final TipoSanguineoService tipoSanguineoService;
    private final ImportacaoService importacaoService;
    public PessoaService(PessoaRepository repository, TelefoneService telefoneService, EnderecoService enderecoService, TipoSanguineoService tipoSanguineoService, ImportacaoService importacaoService) {
        this.repository = repository;
        this.telefoneService = telefoneService;
        this.enderecoService = enderecoService;
        this.tipoSanguineoService = tipoSanguineoService;
        this.importacaoService = importacaoService;
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
    public void salvar(Pessoa pessoa){
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
    private Date _converterStringParaData(String data){
        try{
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            return formatador.parse(data);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<ObesidadePorSexoDTO> obterObesidadePorSexo (Long id) {return repository.calcularObesidadePorSexo(id);    }
    public List<DoadoresPorReceptorDTO> obterDoadoresPorReceptor (Long id) {return repository.calcularQuantidadeDePossiveisDoadoresParaCadaTipoSanguineo(id);}
    public List<CandidatosPorEstadoDTO> obterCandidatosPorEstado(Long id) {return repository.candidatosPorEstado(id);}
    public List<ImcMedioPorFaixaEtariaDTO> obterImcMedioPorFaixaEtaria(Long id){return repository.calcularIMCMedioPorFaixaIdade(id);}
    public List<MediaParaCadaTipoSanguineoDTO> obterMediaPorTipoSanguineo(Long id) { return repository.calcularMediaDeIdadeParaCadaTipoSanguineo(id);}
}
