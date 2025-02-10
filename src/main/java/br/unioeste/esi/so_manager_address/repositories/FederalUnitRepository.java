package br.unioeste.esi.so_manager_address.repositories;

import br.unioeste.esi.so_manager_address.domains.entity.FederalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FederalUnitRepository extends JpaRepository<FederalUnit, Long>, JpaSpecificationExecutor<FederalUnit> {
    Optional<FederalUnit> findByAbbreviation(String abbreviation);
}
