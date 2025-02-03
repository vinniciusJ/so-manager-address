package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.StateDTO;
import br.unioeste.esi.so_manager_address.domains.entity.State;

public class StateMapper {
    public static StateDTO convertEntityToDTO(State state) {
        return StateDTO
                .builder()
                .name(state.getName())
                .abbreviation(state.getAbbreviation())
                .build();
    }

    public static State convertDTOToEntity(StateDTO state) {
        return State
                .builder()
                .name(state.getName())
                .abbreviation(state.getAbbreviation())
                .build();
    }
}
