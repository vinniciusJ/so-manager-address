package br.unioeste.esi.so_manager_address.repositories;

import br.unioeste.esi.so_manager_address.domains.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findById(long id);

    @Query("""
        SELECT a from Address a
        WHERE a.zipCode = :zipCode
    """)
    List<Address> findAllByZipCode(@Param("zipCode") String zipCode);
}
