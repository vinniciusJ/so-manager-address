package br.unioeste.esi.so_manager_address.repositories;

import br.unioste.esi.so_manager.address_lib.domains.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    City findById(long id);
}
