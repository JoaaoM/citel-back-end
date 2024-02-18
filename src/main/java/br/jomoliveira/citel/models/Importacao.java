package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="importacao")
@Data
public class Importacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_IMPORTACAO")
    private Long id;

    @Column(name="NOME")
    private String nome;

    @Column(name="DATA_IMPORTACAO")
    private Date data_importacao;
}
