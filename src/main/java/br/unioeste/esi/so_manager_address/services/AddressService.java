package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.AddressDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Address;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.AddressMapper;
import br.unioeste.esi.so_manager_address.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address create(AddressDTO form, Neighborhood neighborhood, Location location, City city){
        Address address = AddressMapper.convertDTOToEntity(form);

        address.setNeighborhood(neighborhood);
        address.setLocation(location);
        address.setCity(city);

        return addressRepository.save(address);
    }

    public Address update(Long id, AddressDTO form, Neighborhood neighborhood, Location location, City city){
        Address address = findById(id);

        address.setId(id);
        address.setZipCode(form.getZipCode());
        address.setCity(city);
        address.setNeighborhood(neighborhood);
        address.setLocation(location);

        return addressRepository.save(address);
    }

    public void delete(Long id){
        addressRepository.deleteById(id);
    }

    public List<Address> findAll(){
        return addressRepository.findAll();
    }

    public URI createURI(UriComponentsBuilder builder, Address address){
        return builder.path("/address/{id}").buildAndExpand(address.getId()).toUri();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Endereço não encontrado com ID " + id)
        );
    }
}
