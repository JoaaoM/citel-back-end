package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.models.Importacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportacaoRepository extends JpaRepository<Importacao, Long> {

    Importacao findByNome(String nome);
}
