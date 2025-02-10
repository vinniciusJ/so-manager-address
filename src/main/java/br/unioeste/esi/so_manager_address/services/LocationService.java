package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.LocationDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.LocationFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.LocationTypeFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.LocationMapper;
import br.unioeste.esi.so_manager_address.repositories.LocationRepository;
import br.unioeste.esi.so_manager_address.specifications.BaseSpecification;
import br.unioeste.esi.so_manager_address.specifications.Search;
import br.unioeste.esi.so_manager_address.specifications.SpecificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location create(LocationDTO form, LocationType locationType){
        Location location = LocationMapper.convertDTOToEntity(form);

        location.setLocationType(locationType);

        return locationRepository.save(location);
    }

    public Location update(Long id, LocationDTO form, LocationType locationType){
        Location location = findById(id);

        location.setId(id);
        location.setName(form.getName());
        location.setLocationType(locationType);

        return locationRepository.save(location);
    }

    public void delete(Long id){
        locationRepository.deleteById(id);
    }

    public List<Location> findAll(LocationFiltersDTO filters){
        return locationRepository.findAll(generateSpecification(filters));
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Logradouro não encontrado com ID " + id)
        );
    }

    public URI createURI(UriComponentsBuilder builder, Location location) {
        return builder.path("/location/{id}").buildAndExpand(location.getId()).toUri();
    }

    private Specification<Location> generateSpecification(LocationFiltersDTO filters){
        Search<String> locationTypeIdCriteria = SpecificationUtils.generateEqualsCriteria("locationType.name", filters.locationTypeName());
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());

        Specification<Location> locationTypeIdSpecification = new BaseSpecification<>(locationTypeIdCriteria);
        Specification<Location> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(locationTypeIdSpecification.and(nameSpecification));
    }
}
