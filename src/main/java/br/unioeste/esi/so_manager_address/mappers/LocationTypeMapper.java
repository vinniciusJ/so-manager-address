package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.LocationTypeDTO;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;

public class LocationTypeMapper {
    private LocationTypeMapper() {}

    public static LocationType convertDTOToEntity(LocationTypeDTO locationType){
        return LocationType
                .builder()
                .name(locationType.getName())
                .build();
    }

    public static LocationTypeDTO convertEntityToDTO(LocationType locationType){
        return LocationTypeDTO
                .builder()
                .id(locationType.getId())
                .name(locationType.getName())
                .build();
    }
}
