package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.services.PessoaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("analises")
public class AnaliseController {
    private final PessoaService pessoaService;

    public AnaliseController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("{idImportacao}/pessoa-por-estado")
    public Map<String, Long> pessoaPorEstado(@PathVariable Long idImportacao) {
        return pessoaService.pessoaPorEstado(idImportacao);
    }

    @GetMapping("{idImportacao}/calcular-obesidade-por-sexo")
    public Map<String, Long> calcularObesidadePorSexo(@PathVariable Long idImportacao) {
        return pessoaService.calcularObesidadePorSexo(idImportacao);
    }

    @GetMapping("{idImportacao}/media-de-idade-por-tipo-sanguineo")
    public Map<String, Double> mediaIdadeParaCadaTipoSanguineo(@PathVariable Long idImportacao) {
        return pessoaService.mediaIdadeParaCadaTipoSanguineo(idImportacao);
    }
    @GetMapping("{idImportacao}/imc-medio-por-faixa-de-idade-dez-em-dez-anos")
    public Map<String, Double> calcularIMCMedioPorFaixaDeIdade(@PathVariable Long idImportacao) {
        return pessoaService.calcularIMCMedioPorFaixaDeIdade(idImportacao);
    }

    @GetMapping("{idImportacao}/quantidade-de-doadores-por-tipo-sanguineo")
    public Map<String, Double> calcularQuantidadeDoadoresPorTipoSanguineo(@PathVariable Long idImportacao) {
        return pessoaService.calcularQuantidadeDoadoresPorTipoSanguineo(idImportacao);
    }
}
