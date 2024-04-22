package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.PessoaDTO;
import br.jomoliveira.citel.models.TipoSanguineo;
import br.jomoliveira.citel.repositories.TipoSanguineoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoSanguineoService {
    private final TipoSanguineoRepository repository;
    public TipoSanguineoService(TipoSanguineoRepository repository) {
        this.repository = repository;
    }
    public TipoSanguineo findTipoSanguineo (String sorotipagem){
        return repository.findBySorotipagem(sorotipagem);
    }
}
