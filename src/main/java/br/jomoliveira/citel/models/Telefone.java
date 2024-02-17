package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "telefone")
@Data
@EqualsAndHashCode(of = "id")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONE")
    private Long id;

    @Column(name = "FIXO")
    private String fixo;

    @Column(name = "CELULAR")
    private String celular;

    public Telefone (String fixo, String celular){
        this.fixo = fixo;
        this.celular = celular;
    }

    public Telefone () {}
}
