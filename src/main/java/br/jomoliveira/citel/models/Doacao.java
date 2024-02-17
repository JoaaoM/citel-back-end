package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "doacao")
@Data
@EqualsAndHashCode(of = "id")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOACAO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_SANGUINEO_DOADOR")
    private TipoSanguineo tipoSanguineoDoador;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_SANGUINEO_RECEPTOR")
    private TipoSanguineo tipoSanguineoReceptor;
}
