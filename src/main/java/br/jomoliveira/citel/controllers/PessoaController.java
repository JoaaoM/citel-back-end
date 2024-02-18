package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.services.PessoaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    @GetMapping(path = "/candidatos-por-estado/{id}")
    public List<CandidatosPorEstadoDTO> obterCandidatosPorEstado (@PathVariable Long id) {return pessoaService.obterCandidatosPorEstado(id);}
    @GetMapping(path = "/imc-medio-por-faixa-etaria/{id}")
    public List<ImcMedioPorFaixaEtariaDTO> obterImcMedioPorFaixaEtaria(@PathVariable Long id){return pessoaService.obterImcMedioPorFaixaEtaria(id);}
    @GetMapping(path = "/doadores-por-receptor/{id}")
    public List<DoadoresPorReceptorDTO> obterDoadoresPorReceptor (@PathVariable Long id) {return pessoaService.obterDoadoresPorReceptor(id);}
        @GetMapping(path = "/media-por-tipo-sanguineo/{id}")
    public List<MediaParaCadaTipoSanguineoDTO> obterMediaPorTipoSanguineo (@PathVariable Long id) {return pessoaService.obterMediaPorTipoSanguineo(id);}
    @GetMapping(path = "/obesidade-por-sexo/{id}")
    public List<ObesidadePorSexoDTO> obterObesidadePorSexo (@PathVariable Long id) {return pessoaService.obterObesidadePorSexo(id);}

}
