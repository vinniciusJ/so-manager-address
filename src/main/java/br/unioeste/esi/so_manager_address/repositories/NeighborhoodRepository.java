package br.unioeste.esi.so_manager_address.repositories;

import br.unioste.esi.so_manager.address_lib.domains.entities.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long>, JpaSpecificationExecutor<Neighborhood> {
}
