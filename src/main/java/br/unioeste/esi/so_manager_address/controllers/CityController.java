package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.CityDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.CityFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.FederalUnit;
import br.unioeste.esi.so_manager_address.mappers.CityMapper;
import br.unioeste.esi.so_manager_address.services.CityService;
import br.unioeste.esi.so_manager_address.services.FederalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    private final FederalUnitService federalUnitService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAll(CityFiltersDTO filters) {
        List<CityDTO> cities = cityService.findAll(filters).stream().map(CityMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(cities);
    }

    @PostMapping
    public ResponseEntity<CityDTO> create(@RequestBody CityDTO form, UriComponentsBuilder uriBuilder) {
        FederalUnit federalUnit = this.federalUnitService.findByAbbreviation(form.getFederalUnit().getAbbreviation());
        City city = cityService.create(form, federalUnit);

        URI uri = cityService.createURI(uriBuilder, city);

        return ResponseEntity.created(uri).body(CityMapper.convertEntityToDTO(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCity(@PathVariable Long id) {
        City city = cityService.findById(id);

        return ResponseEntity.ok(CityMapper.convertEntityToDTO(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CityDTO form) {
        cityService.update(id, form);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
