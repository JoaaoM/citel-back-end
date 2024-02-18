package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.models.Importacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportacaoRepository extends JpaRepository<Importacao, Long> {

   Optional<Importacao> findById(Long idImportacao);
}
