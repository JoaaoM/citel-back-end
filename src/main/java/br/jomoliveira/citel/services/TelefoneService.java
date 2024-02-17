package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.PessoaDTO;
import br.jomoliveira.citel.models.Telefone;
import org.springframework.stereotype.Service;

@Service
public class TelefoneService {

    public Telefone converterParaEntidade (PessoaDTO pessoaDTO){
        return new Telefone(
                pessoaDTO.telefone_fixo(),
                pessoaDTO.celular()
        );
    }
}
