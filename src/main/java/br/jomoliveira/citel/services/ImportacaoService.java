package br.jomoliveira.citel.services;


import br.jomoliveira.citel.models.Importacao;
import br.jomoliveira.citel.repositories.ImportacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ImportacaoService {
    private final ImportacaoRepository repository;
    public ImportacaoService(ImportacaoRepository repository) {
        this.repository = repository;
    }

    public void salvar(MultipartFile file) {
        Importacao importacao = new Importacao();

        importacao.setNome(file.getName());
        importacao.setData_importacao(new Date());

        salvar(importacao);
    }

    public void salvar(Importacao importacao){
        repository.save(importacao);
    }

    public Importacao findNomeArquivo(String nomeArquivo) {
        return repository.findByNome(nomeArquivo);
    }
}
