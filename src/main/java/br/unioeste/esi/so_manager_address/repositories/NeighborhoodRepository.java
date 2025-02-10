package br.unioeste.esi.so_manager_address.repositories;

import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long>, JpaSpecificationExecutor<Neighborhood> {
}
