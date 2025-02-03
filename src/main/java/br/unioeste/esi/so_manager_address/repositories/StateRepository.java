package br.unioeste.esi.so_manager_address.repositories;

import br.unioeste.esi.so_manager_address.domains.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByAbbreviation(String abbreviation);
}
