package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.LocationDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;
import br.unioeste.esi.so_manager_address.mappers.LocationMapper;
import br.unioeste.esi.so_manager_address.services.LocationService;
import br.unioeste.esi.so_manager_address.services.LocationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationTypeService locationTypeService;

    @GetMapping
    public ResponseEntity<List<LocationDTO>> findAll() {
        List<LocationDTO> locations = locationService.findAll().stream().map(LocationMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(locations);
    }

    @PostMapping
    public ResponseEntity<LocationDTO> save(@RequestBody LocationDTO form, UriComponentsBuilder uriBuilder) {
        LocationType locationType = locationTypeService.findById(form.getLocationType().getId());
        Location location = locationService.create(form, locationType);

        URI uri = locationService.createURI(uriBuilder, location);

        return ResponseEntity.created(uri).body(LocationMapper.convertEntityToDTO(location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> findById(@PathVariable Long id) {
        Location location = locationService.findById(id);

        return ResponseEntity.ok(LocationMapper.convertEntityToDTO(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody LocationDTO form) {
        LocationType locationType = locationTypeService.findById(form.getLocationType().getId());

        locationService.update(id, form, locationType);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
