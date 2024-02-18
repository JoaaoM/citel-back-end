package br.jomoliveira.citel.dtos;

import br.jomoliveira.citel.models.Importacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record DadosImportacaoDTO(Long id, String nomeArquivo, @JsonFormat(pattern = "dd/MM/yyyy") Date dataImportacao) {
    public DadosImportacaoDTO(Importacao importacao) {
        this(importacao.getId(), importacao.getNome(), importacao.getData_importacao());
    }
}

