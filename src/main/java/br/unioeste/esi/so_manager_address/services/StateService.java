package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.StateDTO;
import br.unioeste.esi.so_manager_address.domains.entity.State;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.StateMapper;
import br.unioeste.esi.so_manager_address.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    public List<State> findAll(){
        return stateRepository.findAll();
    }

    public State create(StateDTO form){
        return stateRepository.save(StateMapper.convertDTOToEntity(form));
    }

    public State update(String abbreviation, StateDTO form){
        State state = findById(abbreviation);

        state.setName(form.getName());
        state.setAbbreviation(form.getAbbreviation());

        return stateRepository.save(state);
    }

    public void delete(String abbreviation){
        State state = findById(abbreviation);

        stateRepository.delete(state);
    }

    public URI createURI(UriComponentsBuilder uriBuilder, State state){
        return uriBuilder.path("/state/{abbreviation}").buildAndExpand(state.getAbbreviation()).toUri();
    }

    public State findById(String abbreviation){
        return stateRepository.findByAbbreviation(abbreviation).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "UF não encontrado para sigla " + abbreviation)
        );
    }

}
