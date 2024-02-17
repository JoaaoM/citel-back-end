package br.jomoliveira.citel.dtos;

public record PessoaDTO(
        String nome,
        String cpf,
        String rg,
        String data_nasc,
        String sexo,
        String mae,
        String pai,
        String email,
        String cep,
        String endereco,
        int numero,
        String bairro,
        String cidade,
        String estado,
        String telefone_fixo,
        String celular,
        float altura,
        int peso,
        String tipo_sanguineo
) {}
