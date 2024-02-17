package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "endereco")
@Data
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "NUMERO")
    private Integer numero;

    public Endereco(String estado, String cidade, String bairro, String rua, Integer numero){
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua    = rua;
        this.numero = numero;
    }

    public Endereco() {};
}