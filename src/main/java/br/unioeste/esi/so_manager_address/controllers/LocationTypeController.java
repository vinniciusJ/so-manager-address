package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.LocationTypeDTO;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;
import br.unioeste.esi.so_manager_address.mappers.LocationTypeMapper;
import br.unioeste.esi.so_manager_address.services.LocationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/location-type")
@RequiredArgsConstructor
public class LocationTypeController {
    private final LocationTypeService locationTypeService;

    @GetMapping
    public ResponseEntity<List<LocationTypeDTO>> findAll(){
        List<LocationTypeDTO> locationTypes = locationTypeService.findAll().stream().map(LocationTypeMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(locationTypes);
    }

    @PostMapping
    public ResponseEntity<LocationTypeDTO> create(@RequestBody LocationTypeDTO form, UriComponentsBuilder uriBuilder){
        LocationType locationType = locationTypeService.create(form);
        URI uri = locationTypeService.createURI(uriBuilder, locationType);

        return ResponseEntity.created(uri).body(LocationTypeMapper.convertEntityToDTO(locationType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationTypeDTO> findById(@PathVariable Long id){
        LocationType locationType = locationTypeService.findById(id);

        return ResponseEntity.ok(LocationTypeMapper.convertEntityToDTO(locationType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationTypeDTO> update(@PathVariable Long id, @RequestBody LocationTypeDTO form){
        LocationType locationType = locationTypeService.update(id, form);

        return ResponseEntity.ok(LocationTypeMapper.convertEntityToDTO(locationType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        locationTypeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
