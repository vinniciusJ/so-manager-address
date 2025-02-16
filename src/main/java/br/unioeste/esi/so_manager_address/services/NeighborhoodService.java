package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.repositories.NeighborhoodRepository;
import br.unioeste.esi.so_manager_address.specifications.BaseSpecification;
import br.unioeste.esi.so_manager_address.specifications.Search;
import br.unioeste.esi.so_manager_address.specifications.SpecificationUtils;
import br.unioste.esi.so_manager.address_lib.domains.dtos.NeighborhoodDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.filters.NeighborhoodFiltersDTO;
import br.unioste.esi.so_manager.address_lib.domains.entities.Neighborhood;
import br.unioste.esi.so_manager.address_lib.mappers.NeighborhoodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {
    private final NeighborhoodRepository neighborhoodRepository;

    public Neighborhood create(NeighborhoodDTO form){
        return neighborhoodRepository.save(NeighborhoodMapper.convertDTOToEntity(form));
    }

    public Neighborhood update(Long id, NeighborhoodDTO form){
        Neighborhood neighborhood = findById(id);

        neighborhood.setId(id);
        neighborhood.setName(form.getName());

        return neighborhoodRepository.save(neighborhood);
    }

    public void delete(Long id){
        neighborhoodRepository.deleteById(id);
    }

    public List<Neighborhood> findAll(NeighborhoodFiltersDTO filters){
        return neighborhoodRepository.findAll(generateSpecification(filters));
    }

    public Neighborhood findById(Long id) {
        return neighborhoodRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Bairro não encontrado com ID " + id)
        );
    }

    public URI createURI(UriComponentsBuilder builder, Neighborhood neighborhood) {
        return builder.path("/neighborhoods/{id}").buildAndExpand(neighborhood.getId()).toUri();
    }

    private Specification<Neighborhood> generateSpecification(NeighborhoodFiltersDTO filters){
        Search<String> nameCriteria = SpecificationUtils.generateLeftLikeCriteria("name", filters.name());
        Specification<Neighborhood> nameSpecification = new BaseSpecification<>(nameCriteria);

        return Specification.where(nameSpecification);
    }
}
