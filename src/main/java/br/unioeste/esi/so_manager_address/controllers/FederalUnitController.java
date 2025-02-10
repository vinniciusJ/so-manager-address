package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.FederalUnitDTO;
import br.unioeste.esi.so_manager_address.domains.dto.filters.FederalUnitFiltersDTO;
import br.unioeste.esi.so_manager_address.domains.entity.FederalUnit;
import br.unioeste.esi.so_manager_address.mappers.FederalUnitMapper;
import br.unioeste.esi.so_manager_address.services.FederalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("state")
@RequiredArgsConstructor
public class FederalUnitController {
    private final FederalUnitService federalUnitService;

    @GetMapping
    public ResponseEntity<List<FederalUnitDTO>> findAll(FederalUnitFiltersDTO filters) {
        List<FederalUnit> federalUnits = federalUnitService.findAll(filters);
        List<FederalUnitDTO> statesResponse = federalUnits.stream().map(FederalUnitMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(statesResponse);
    }

    @PostMapping
    public ResponseEntity<FederalUnitDTO> create(@RequestBody FederalUnitDTO form, UriComponentsBuilder uriBuilder) {
        FederalUnit federalUnit = federalUnitService.create(form);
        URI uri = federalUnitService.createURI(uriBuilder, federalUnit);

        return ResponseEntity.created(uri).body(FederalUnitMapper.convertEntityToDTO(federalUnit));
    }

    @GetMapping("/{abbreviation}")
    public ResponseEntity<FederalUnitDTO> findById(@PathVariable String abbreviation) {
        FederalUnit federalUnit = federalUnitService.findByAbbreviation(abbreviation);

        return ResponseEntity.ok(FederalUnitMapper.convertEntityToDTO(federalUnit));
    }

    @PutMapping("/{abbreviation}")
    public ResponseEntity<FederalUnitDTO> update(@PathVariable String abbreviation, @RequestBody FederalUnitDTO form) {
        FederalUnit federalUnit = federalUnitService.update(abbreviation, form);

        return ResponseEntity.ok(FederalUnitMapper.convertEntityToDTO(federalUnit));
    }

    @DeleteMapping("/{abbreviation}")
    public ResponseEntity<FederalUnitDTO> delete(@PathVariable String abbreviation) {
        federalUnitService.delete(abbreviation);

        return ResponseEntity.noContent().build();
    }
}
