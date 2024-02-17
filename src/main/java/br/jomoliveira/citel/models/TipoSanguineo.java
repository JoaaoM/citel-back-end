package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tipo_sanguineo")
@Data
@EqualsAndHashCode(of = "id")
public class TipoSanguineo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_sanguineo")
    private Long id;

    @Column(name = "sorotipagem")
    private String sorotipagem;

    public TipoSanguineo (String sorotipagem){
        this.sorotipagem = sorotipagem;
    }
    public TipoSanguineo () {};
}
