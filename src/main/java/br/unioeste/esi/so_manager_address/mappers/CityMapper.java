package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.CityDTO;
import br.unioeste.esi.so_manager_address.domains.dto.StateDTO;
import br.unioeste.esi.so_manager_address.domains.entity.City;

public class CityMapper {
    private CityMapper(){}

    public static CityDTO convertEntityToDTO(City city){
        StateDTO state = StateMapper.convertEntityToDTO(city.getState());

        return CityDTO
                .builder()
                .id(city.getId())
                .name(city.getName())
                .state(state)
                .build();
    }

    public static City convertDTOToEntity(CityDTO cityDTO){
        return City.builder().name(cityDTO.getName()).build();
    }
}
