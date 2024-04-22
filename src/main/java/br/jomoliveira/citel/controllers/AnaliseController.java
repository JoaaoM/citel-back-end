package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.exceptions.ImportacaoException;
import br.jomoliveira.citel.services.ImportacaoService;
import br.jomoliveira.citel.services.PessoaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("analises")
public class AnaliseController {
    private final PessoaService pessoaService;
    private final ImportacaoService importacaoService;

    private static final String MENSAGEM_ID_IMPORTACAO_NAO_ENCONTRADO = "O ID de importação não foi encontrado. Por favor, insira um ID válido e tente novamente.";

    public AnaliseController(PessoaService pessoaService, ImportacaoService importacaoService) {
        this.pessoaService = pessoaService;
        this.importacaoService = importacaoService;
    }

    @GetMapping("{idImportacao}/pessoa-por-estado")
    public List<CandidatosPorEstadoDTO> pessoaPorEstado(@PathVariable Long idImportacao) {
        _validar(idImportacao);
        return pessoaService.pessoaPorEstado(idImportacao);
    }

    @GetMapping("{idImportacao}/calcular-obesidade-por-sexo")
    public List<ObesidadePorSexoDTO> calcularObesidadePorSexo(@PathVariable Long idImportacao) {
        _validar(idImportacao);
        return pessoaService.calcularObesidadePorSexo(idImportacao);
    }

    @GetMapping("{idImportacao}/media-de-idade-por-tipo-sanguineo")
    public List<MediaParaCadaTipoSanguineoDTO> mediaIdadeParaCadaTipoSanguineo(@PathVariable Long idImportacao) {
        _validar(idImportacao);
        return pessoaService.mediaIdadeParaCadaTipoSanguineo(idImportacao);
    }

    @GetMapping("{idImportacao}/imc-medio-por-faixa-de-idade-dez-em-dez-anos")
    public List<ImcMedioPorFaixaEtariaDTO> calcularIMCMedioPorFaixaDeIdade(@PathVariable Long idImportacao) {
        _validar(idImportacao);
        return pessoaService.calcularIMCMedioPorFaixaDeIdade(idImportacao);
    }

    @GetMapping("{idImportacao}/quantidade-de-doadores-por-tipo-sanguineo")
    public List<DoadoresPorReceptorDTO>  calcularQuantidadeDoadoresPorTipoSanguineo(@PathVariable Long idImportacao) {
        _validar(idImportacao);
        return pessoaService.calcularQuantidadeDoadoresPorTipoSanguineo(idImportacao);
    }
    private void _validar(Long idImportacao){
        if(importacaoService.findById(idImportacao) == null) {
            throw new ImportacaoException(MENSAGEM_ID_IMPORTACAO_NAO_ENCONTRADO);
        }
    }
}
