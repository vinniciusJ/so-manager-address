package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.exceptions.AddressException;

import br.unioeste.esi.so_manager_address.repositories.AddressRepository;
import br.unioeste.esi.so_manager_address.specifications.BaseSpecification;
import br.unioeste.esi.so_manager_address.specifications.Search;
import br.unioeste.esi.so_manager_address.specifications.SpecificationUtils;
import br.unioste.esi.so_manager.address_lib.domains.dtos.AddressDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.external.ExternalAddressDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.filters.AddressFiltersDTO;
import br.unioste.esi.so_manager.address_lib.domains.entities.Address;
import br.unioste.esi.so_manager.address_lib.domains.entities.City;
import br.unioste.esi.so_manager.address_lib.domains.entities.Location;
import br.unioste.esi.so_manager.address_lib.domains.entities.Neighborhood;
import br.unioste.esi.so_manager.address_lib.mappers.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final WebClient webClient;

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

    public List<Address> findAll(AddressFiltersDTO filters){
        return addressRepository.findAll(generateSpecification(filters));
    }

    public AddressDTO findExternalAddressByZipCode(String zipCode){
        ExternalAddressDTO externalAddress = webClient
                .get()
                .uri("https://viacep.com.br/ws/" + zipCode + "/json")
                .retrieve()
                .bodyToMono(ExternalAddressDTO.class)
                .block();

        if(externalAddress == null){
            throw new AddressException(HttpStatus.NOT_FOUND, "Endereço externo não encontrado com CEP " + zipCode);
        }

        return AddressMapper.convertExternalAddressDTO(externalAddress);
    }

    public URI createURI(UriComponentsBuilder builder, Address address){
        return builder.path("/address/{id}").buildAndExpand(address.getId()).toUri();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Endereço não encontrado com ID " + id)
        );
    }

    private Specification<Address> generateSpecification(AddressFiltersDTO filters){
        Search<String> zipCodeCriteria = SpecificationUtils.generateLeftLikeCriteria("zipCode", filters.zipCode());
        Search<String> neighborhoodCriteria = SpecificationUtils.generateLeftLikeCriteria("neighborhood.name", filters.neighborhoodName());
        Search<String> locationCriteria = SpecificationUtils.generateLeftLikeCriteria("location.name", filters.locationName());
        Search<String> cityCriteria = SpecificationUtils.generateLeftLikeCriteria("city.name", filters.cityName());
        Search<String> federalUnitCriteria = SpecificationUtils.generateLeftLikeCriteria("city.federalUnit.name", filters.federalUnitName());

        Specification<Address> zipCodeSpecification = new BaseSpecification<>(zipCodeCriteria);
        Specification<Address> neighborhoodIdSpecification = new BaseSpecification<>(neighborhoodCriteria);
        Specification<Address> locationIdSpecification = new BaseSpecification<>(locationCriteria);
        Specification<Address> cityIdSpecification = new BaseSpecification<>(cityCriteria);
        Specification<Address> federalUnitSpecification = new BaseSpecification<>(federalUnitCriteria);

        return Specification.where(zipCodeSpecification).and(neighborhoodIdSpecification).and(locationIdSpecification).and(cityIdSpecification).and(federalUnitSpecification);
    }
}
