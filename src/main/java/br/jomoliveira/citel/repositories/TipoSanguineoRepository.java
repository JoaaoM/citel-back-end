package br.jomoliveira.citel.repositories;

import br.jomoliveira.citel.models.TipoSanguineo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoSanguineoRepository extends JpaRepository<TipoSanguineo, Long> {
    TipoSanguineo findBySorotipagem(String sorotipagem);
}
