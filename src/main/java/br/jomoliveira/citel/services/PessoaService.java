package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.models.Pessoa;
import br.jomoliveira.citel.repositories.PessoaRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void salvar(List<PessoaDTO> listPessoaDTO, String nomeArquivo) {
        listPessoaDTO.forEach(pessoaDTO -> {
            salvar(converterParaEntidade(pessoaDTO, nomeArquivo));
        });
    }
    public void salvar(Pessoa pessoa){
        repository.save(pessoa);
    }
    public Pessoa converterParaEntidade(PessoaDTO pessoaDTO, String nomeArquivo) {
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
                importacaoService.findNomeArquivo(nomeArquivo)
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
    public List<ObesidadePorSexoDTO> obterObesidadePorSexo () {return repository.calcularObesidadePorSexo();    }
    public List<DoadoresPorReceptorDTO> obterDoadoresPorReceptor () {return repository.calcularQuantidadeDePossiveisDoadoresParaCadaTipoSanguineo();}
    public List<CandidatosPorEstadoDTO> obterCandidatosPorEstado() {
        return repository.candidatosPorEstado();
    }
    public List<ImcMedioPorFaixaEtariaDTO> obterImcMedioPorFaixaEtaria(){return repository.calcularIMCMedioPorFaixaIdade();}
    public List<MediaParaCadaTipoSanguineoDTO> obterMediaPorTipoSanguineo() { return repository.calcularMediaDeIdadeParaCadaTipoSanguineo();}
}
