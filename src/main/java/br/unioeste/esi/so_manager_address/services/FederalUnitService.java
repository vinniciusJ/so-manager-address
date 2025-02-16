package br.unioeste.esi.so_manager_address.services;


import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.repositories.FederalUnitRepository;
import br.unioeste.esi.so_manager_address.specifications.BaseSpecification;
import br.unioeste.esi.so_manager_address.specifications.Search;
import br.unioeste.esi.so_manager_address.specifications.SpecificationUtils;
import br.unioste.esi.so_manager.address_lib.domains.dtos.FederalUnitDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.filters.FederalUnitFiltersDTO;
import br.unioste.esi.so_manager.address_lib.domains.entities.FederalUnit;
import br.unioste.esi.so_manager.address_lib.mappers.FederalUnitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FederalUnitService {
    private final FederalUnitRepository federalUnitRepository;

    public List<FederalUnit> findAll(FederalUnitFiltersDTO filters){
        return federalUnitRepository.findAll(generateSpecification(filters));
    }

    public FederalUnit create(FederalUnitDTO form){
        return federalUnitRepository.save(FederalUnitMapper.convertDTOToEntity(form));
    }

    public FederalUnit update(String abbreviation, FederalUnitDTO form){
        FederalUnit federalUnit = findByAbbreviation(abbreviation);

        federalUnit.setName(form.getName());
        federalUnit.setAbbreviation(form.getAbbreviation());

        return federalUnitRepository.save(federalUnit);
    }

    public void delete(String abbreviation){
        FederalUnit federalUnit = findByAbbreviation(abbreviation);

        federalUnitRepository.delete(federalUnit);
    }

    public URI createURI(UriComponentsBuilder uriBuilder, FederalUnit federalUnit){
        return uriBuilder.path("/state/{abbreviation}").buildAndExpand(federalUnit.getAbbreviation()).toUri();
    }

    public FederalUnit findByAbbreviation(String abbreviation){
        return federalUnitRepository.findByAbbreviation(abbreviation).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "UF não encontrado para sigla " + abbreviation)
        );
    }

    private Specification<FederalUnit> generateSpecification(FederalUnitFiltersDTO filters){
        Search<String> abbreviationCriteria = SpecificationUtils.generateEqualsCriteria("abbreviation", filters.name());
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());

        Specification<FederalUnit> abbreviationSpecification = new BaseSpecification<>(abbreviationCriteria);
        Specification<FederalUnit> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(abbreviationSpecification.and(nameSpecification));
    }
}
