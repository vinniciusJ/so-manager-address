package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.NeighborhoodDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.NeighborhoodMapper;
import br.unioeste.esi.so_manager_address.repositories.NeighborhoodRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Neighborhood> findAll(){
        return neighborhoodRepository.findAll();
    }

    public Neighborhood findById(Long id) {
        return neighborhoodRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Bairro não encontrado com ID " + id)
        );
    }

    public URI createURI(UriComponentsBuilder builder, Neighborhood neighborhood) {
        return builder.path("/neighborhoods/{id}").buildAndExpand(neighborhood.getId()).toUri();
    }
}
