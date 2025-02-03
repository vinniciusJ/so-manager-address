package br.unioeste.esi.so_manager_address.repositories;

import br.unioeste.esi.so_manager_address.domains.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findById(long id);
}
