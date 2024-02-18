package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query(value = "SELECT NEW br.jomoliveira.citel.dtos.CandidatosPorEstadoDTO(p.endereco.estado, count(p)) FROM Pessoa p WHERE p.importacao.id = :id GROUP BY p.endereco.estado")
    List<CandidatosPorEstadoDTO> candidatosPorEstado(@Param("id") Long id);

    @Query("SELECT " +
            "NEW br.jomoliveira.citel.dtos.ImcMedioPorFaixaEtariaDTO(" +
            "CASE WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 0 AND 10 THEN '0-10' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 11 AND 20 THEN '11-20' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 21 AND 30 THEN '21-30' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 31 AND 40 THEN '31-40' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 41 AND 50 THEN '41-50' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 51 AND 60 THEN '51-60' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 61 AND 70 THEN '61-70' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 71 AND 80 THEN '71-80' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 81 AND 90 THEN '81-90' " +
            "ELSE '91+' END, " +
            "AVG(p.peso / (p.altura * p.altura))) " +
            "FROM Pessoa p " +
            "WHERE p.importacao.id = :id " +
            "GROUP BY " +
            "CASE WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 0 AND 10 THEN '0-10' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 11 AND 20 THEN '11-20' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 21 AND 30 THEN '21-30' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 31 AND 40 THEN '31-40' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 41 AND 50 THEN '41-50' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 51 AND 60 THEN '51-60' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 61 AND 70 THEN '61-70' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 71 AND 80 THEN '71-80' " +
            "WHEN YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento) BETWEEN 81 AND 90 THEN '81-90' " +
            "ELSE '91+' END ")
    List<ImcMedioPorFaixaEtariaDTO> calcularIMCMedioPorFaixaIdade(@Param("id") Long id);


    @Query(value = "SELECT NEW br.jomoliveira.citel.dtos.MediaParaCadaTipoSanguineoDTO(p.tipoSanguineo.sorotipagem, AVG(YEAR(CURRENT_DATE()) - YEAR(p.dataNascimento))) FROM Pessoa p GROUP BY p.tipoSanguineo.sorotipagem")
    List<MediaParaCadaTipoSanguineoDTO> calcularMediaDeIdadeParaCadaTipoSanguineo(@Param("id") Long id);

    @Query("SELECT NEW br.jomoliveira.citel.dtos.DoadoresPorReceptorDTO(t.sorotipagem, COUNT(p)) " +
            "FROM Doacao d " +
            "JOIN d.tipoSanguineoReceptor t " +
            "JOIN Pessoa p ON d.tipoSanguineoDoador = p.tipoSanguineo " +
            "WHERE p.importacao.id = :id " +
            "GROUP BY t.sorotipagem")
    List<DoadoresPorReceptorDTO> calcularQuantidadeDePossiveisDoadoresParaCadaTipoSanguineo(@Param("id") Long id);

    @Query("SELECT NEW br.jomoliveira.citel.dtos.ObesidadePorSexoDTO(p.sexo, " +
            "COUNT(p), " +
            "SUM(CASE WHEN (p.peso / (p.altura * p.altura)) > 30 THEN 1 ELSE 0 END), " +
            "CAST(SUM(CASE WHEN (p.peso / (p.altura * p.altura)) > 30 THEN 1 ELSE 0 END) / COUNT(*) * 100 AS DOUBLE)) " +
            "FROM Pessoa p WHERE p.importacao.id = :id GROUP BY p.sexo")
    List<ObesidadePorSexoDTO> calcularObesidadePorSexo(@Param("id") Long id);


}