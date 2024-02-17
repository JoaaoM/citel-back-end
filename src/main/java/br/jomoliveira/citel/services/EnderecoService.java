package br.jomoliveira.citel.services;

import br.jomoliveira.citel.dtos.PessoaDTO;
import br.jomoliveira.citel.models.Endereco;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    public Endereco converterParaEntidade (PessoaDTO pessoaDTO){
        return new Endereco(
                pessoaDTO.estado(),
                pessoaDTO.cidade(),
                pessoaDTO.bairro(),
                pessoaDTO.endereco(), // rua
                pessoaDTO.numero()
        );
    }
}
