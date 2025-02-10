package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.CityDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.CityFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.FederalUnit;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.CityMapper;
import br.unioeste.esi.so_manager_address.mappers.FederalUnitMapper;
import br.unioeste.esi.so_manager_address.repositories.CityRepository;
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
public class CityService {
    private final CityRepository cityRepository;

    public City create(CityDTO form, FederalUnit federalUnit) {
        City city = CityMapper.convertDTOToEntity(form);

        city.setFederalUnit(federalUnit);

        return cityRepository.save(city);
    }

    public List<City> findAll(CityFiltersDTO filters){
        return cityRepository.findAll(generateSpecification(filters));
    }

    public City update(Long id, CityDTO form) {
        City city = findById(id);
        FederalUnit federalUnit = FederalUnitMapper.convertDTOToEntity(form.getFederalUnit());

        city.setName(form.getName());
        city.setFederalUnit(federalUnit);

        return cityRepository.save(city);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    public URI createURI(UriComponentsBuilder builder, City city) {
        return builder.path("/city/{id}").buildAndExpand(city.getId()).toUri();
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Cidade não encontrada com ID " + id)
        );
    }

    private Specification<City> generateSpecification(CityFiltersDTO filters){
        Search<String> stateAbbreviationCriteria = SpecificationUtils.generateEqualsCriteria("federalUnit.abbreviation", filters.federalUnitAbbreviation());
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());

        Specification<City> stateAbbreviationSpecification = new BaseSpecification<>(stateAbbreviationCriteria);
        Specification<City> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(stateAbbreviationSpecification.and(nameSpecification));
    }
}
