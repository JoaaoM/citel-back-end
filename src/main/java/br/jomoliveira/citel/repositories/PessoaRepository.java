package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query(value = "SELECT p FROM Pessoa p WHERE p.importacao.id = :id")
    List<Pessoa> buscarTodos(@Param("id") Long id);
}