package br.jomoliveira.citel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Table(name="pessoa")
@Entity
@Data
@EqualsAndHashCode(of="id")
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "RG")
    private String rg;

    @Column(name = "DATA_NASC")
    private LocalDate dataNascimento;

    @Column(name = "SEXO")
    private String sexo;

    @Column(name = "MAE")
    private String mae;

    @Column(name = "PAI")
    private String pai;

    @Column(name = "ALTURA")
    private Float altura;

    @Column(name = "PESO")
    private Integer peso;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_TIPO_SANGUINEO")
    private TipoSanguineo tipoSanguineo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_TELEFONE")
    private Telefone telefone;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="ID_IMPORTACAO")
    private Importacao importacao;

    public Pessoa(String nome, String cpf, String rg, LocalDate dataNascimento, String sexo, String mae, String pai, Float altura, Integer peso, TipoSanguineo tipoSanguineo, Endereco endereco, Telefone telefone, Importacao importacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.mae = mae;
        this.pai = pai;
        this.altura = altura;
        this.peso = peso;
        this.tipoSanguineo = tipoSanguineo;
        this.endereco = endereco;
        this.telefone = telefone;
        this.importacao = importacao;
    }

    public Float getImc(){
        if(altura == null) return 0F;
        if(peso == null) return 0F;
        return peso/altura*altura;
    }

    public int getIdade() {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }
}
