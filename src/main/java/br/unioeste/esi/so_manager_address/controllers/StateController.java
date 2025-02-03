package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.StateDTO;
import br.unioeste.esi.so_manager_address.domains.entity.State;
import br.unioeste.esi.so_manager_address.mappers.StateMapper;
import br.unioeste.esi.so_manager_address.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("state")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping
    public ResponseEntity<List<StateDTO>> findAll() {
        List<State> states = stateService.findAll();
        List<StateDTO> statesResponse = states.stream().map(StateMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(statesResponse);
    }

    @PostMapping
    public ResponseEntity<StateDTO> create(@RequestBody StateDTO form, UriComponentsBuilder uriBuilder) {
        State state = stateService.create(form);
        URI uri = stateService.createURI(uriBuilder, state);

        return ResponseEntity.created(uri).body(StateMapper.convertEntityToDTO(state));
    }

    @GetMapping("/{abbreviation}")
    public ResponseEntity<StateDTO> findById(@PathVariable String abbreviation) {
        State state = stateService.findById(abbreviation);

        return ResponseEntity.ok(StateMapper.convertEntityToDTO(state));
    }

    @PutMapping("/{abbreviation}")
    public ResponseEntity<StateDTO> update(@PathVariable String abbreviation, @RequestBody StateDTO form) {
        State state = stateService.update(abbreviation, form);

        return ResponseEntity.ok(StateMapper.convertEntityToDTO(state));
    }

    @DeleteMapping("/{abbreviation}")
    public ResponseEntity<StateDTO> delete(@PathVariable String abbreviation) {
        stateService.delete(abbreviation);

        return ResponseEntity.noContent().build();
    }
}
