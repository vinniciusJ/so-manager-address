package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.LocationTypeDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.LocationTypeFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.LocationTypeMapper;
import br.unioeste.esi.so_manager_address.repositories.LocationTypeRepository;
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
public class LocationTypeService {
    private final LocationTypeRepository locationTypeRepository;

    public LocationType create(LocationTypeDTO form){
        return locationTypeRepository.save(LocationTypeMapper.convertDTOToEntity(form));
    }

    public LocationType update(Long id, LocationTypeDTO form){
        LocationType locationType = findById(id);

        locationType.setId(id);
        locationType.setName(form.getName());

        return locationTypeRepository.save(locationType);
    }

    public List<LocationType> findAll(LocationTypeFiltersDTO filters){
        return locationTypeRepository.findAll(generateSpecification(filters));
    }

    public void delete(Long id){
        locationTypeRepository.deleteById(id);
    }

    public URI createURI(UriComponentsBuilder builder, LocationType locationType){
        return builder.path("/location-type/{id}").buildAndExpand(locationType.getId()).toUri();
    }

    public LocationType findById(Long id) {
        return locationTypeRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Tipo de logradouro não encontrado com ID " + id)
        );
    }

    private Specification<LocationType> generateSpecification(LocationTypeFiltersDTO filters){
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());
        Specification<LocationType> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(nameSpecification);
    }
}
