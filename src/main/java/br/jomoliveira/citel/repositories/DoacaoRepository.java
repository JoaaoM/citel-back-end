package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.models.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}
