package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.FederalUnitDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.FederalUnitFiltersDTO;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.FederalUnitMapper;
import br.unioeste.esi.so_manager_address.repositories.FederalUnitRepository;
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
public class FederalUnitService {
    private final FederalUnitRepository federalUnitRepository;

    public List<br.unioeste.esi.so_manager_address.domains.entity.FederalUnit> findAll(FederalUnitFiltersDTO filters){
        return federalUnitRepository.findAll(generateSpecification(filters));
    }

    public br.unioeste.esi.so_manager_address.domains.entity.FederalUnit create(FederalUnitDTO form){
        return federalUnitRepository.save(FederalUnitMapper.convertDTOToEntity(form));
    }

    public br.unioeste.esi.so_manager_address.domains.entity.FederalUnit update(String abbreviation, FederalUnitDTO form){
        br.unioeste.esi.so_manager_address.domains.entity.FederalUnit federalUnit = findByAbbreviation(abbreviation);

        federalUnit.setName(form.getName());
        federalUnit.setAbbreviation(form.getAbbreviation());

        return federalUnitRepository.save(federalUnit);
    }

    public void delete(String abbreviation){
        br.unioeste.esi.so_manager_address.domains.entity.FederalUnit federalUnit = findByAbbreviation(abbreviation);

        federalUnitRepository.delete(federalUnit);
    }

    public URI createURI(UriComponentsBuilder uriBuilder, br.unioeste.esi.so_manager_address.domains.entity.FederalUnit federalUnit){
        return uriBuilder.path("/state/{abbreviation}").buildAndExpand(federalUnit.getAbbreviation()).toUri();
    }

    public br.unioeste.esi.so_manager_address.domains.entity.FederalUnit findByAbbreviation(String abbreviation){
        return federalUnitRepository.findByAbbreviation(abbreviation).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "UF não encontrado para sigla " + abbreviation)
        );
    }

    private Specification<br.unioeste.esi.so_manager_address.domains.entity.FederalUnit> generateSpecification(FederalUnitFiltersDTO filters){
        Search<String> abbreviationCriteria = SpecificationUtils.generateEqualsCriteria("abbreviation", filters.name());
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());

        Specification<br.unioeste.esi.so_manager_address.domains.entity.FederalUnit> abbreviationSpecification = new BaseSpecification<>(abbreviationCriteria);
        Specification<br.unioeste.esi.so_manager_address.domains.entity.FederalUnit> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(abbreviationSpecification.and(nameSpecification));
    }
}
