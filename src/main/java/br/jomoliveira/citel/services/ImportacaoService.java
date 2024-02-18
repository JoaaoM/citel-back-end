package br.jomoliveira.citel.services;


import br.jomoliveira.citel.dtos.DadosImportacaoDTO;
import br.jomoliveira.citel.models.Importacao;
import br.jomoliveira.citel.repositories.ImportacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImportacaoService {
    private final ImportacaoRepository repository;
    public ImportacaoService(ImportacaoRepository repository) {
        this.repository = repository;
    }

    public Long salvar(MultipartFile file) {
        Importacao importacao = new Importacao();

        importacao.setNome(file.getOriginalFilename());
        importacao.setData_importacao(new Date());

        return salvar(importacao);
    }

    public Long salvar(Importacao importacao){
        repository.save(importacao);
        return importacao.getId();
    }

    public Importacao findById(Long idImportacao) {
        Optional<Importacao> optionalImportacao = repository.findById(idImportacao);
        return optionalImportacao.orElse(null);
    }

    public List<DadosImportacaoDTO> listarDadosImportacao() {
        return repository.findAll().stream()
                .map(DadosImportacaoDTO::new)
                .collect(Collectors.toList());
    }
}
