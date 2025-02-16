package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.services.NeighborhoodService;
import br.unioste.esi.so_manager.address_lib.domains.dtos.NeighborhoodDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.filters.NeighborhoodFiltersDTO;
import br.unioste.esi.so_manager.address_lib.domains.entities.Neighborhood;
import br.unioste.esi.so_manager.address_lib.mappers.NeighborhoodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/neighborhood")
@RequiredArgsConstructor
public class NeighborhoodController {
    private final NeighborhoodService neighborhoodService;

    @GetMapping
    public ResponseEntity<List<NeighborhoodDTO>> findAll(NeighborhoodFiltersDTO filters){
        List<NeighborhoodDTO> neighborhoods = neighborhoodService.findAll(filters).stream().map(NeighborhoodMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(neighborhoods);
    }

    @PostMapping
    public ResponseEntity<NeighborhoodDTO> create(@RequestBody NeighborhoodDTO form, UriComponentsBuilder uriBuilder){
        Neighborhood neighborhood = neighborhoodService.create(form);
        URI uri = neighborhoodService.createURI(uriBuilder, neighborhood);

        return ResponseEntity.created(uri).body(NeighborhoodMapper.convertEntityToDTO(neighborhood));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeighborhoodDTO> findById(@PathVariable Long id){
        Neighborhood neighborhood = neighborhoodService.findById(id);

        return ResponseEntity.ok(NeighborhoodMapper.convertEntityToDTO(neighborhood));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NeighborhoodDTO> update(@PathVariable Long id, @RequestBody NeighborhoodDTO form){
        neighborhoodService.update(id, form);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        neighborhoodService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
