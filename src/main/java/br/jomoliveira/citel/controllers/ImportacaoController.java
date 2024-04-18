package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.dtos.DadosImportacaoDTO;
import br.jomoliveira.citel.services.ImportacaoService;
import br.jomoliveira.citel.services.PessoaService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("importacoes")
public class ImportacaoController {
    private final ImportacaoService importacaoService;
    private final PessoaService pessoaService;
    public ImportacaoController(ImportacaoService importacaoService, PessoaService pessoaService) {
        this.importacaoService = importacaoService;
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> importarPessoas(@RequestParam("jsonFile") MultipartFile file) {
        try {
            String json = new String(file.getBytes());
            var idImportacao = importacaoService.salvar(file);
            pessoaService.salvar( json, idImportacao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoas importadas com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar pessoas.");
        }
    }

    @GetMapping
    public ResponseEntity<List<DadosImportacaoDTO>> obterDadosImportacao() {
        return ResponseEntity.status(HttpStatus.OK).body(importacaoService.listarDadosImportacao());
    }
}
