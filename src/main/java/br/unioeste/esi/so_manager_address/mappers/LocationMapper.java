package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.LocationDTO;
import br.unioeste.esi.so_manager_address.domains.dto.LocationTypeDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.LocationType;

public class LocationMapper {
    private LocationMapper() {}

    public static Location convertDTOToEntity(LocationDTO location) {
        LocationType locationType = LocationTypeMapper.convertDTOToEntity(location.getLocationType());

        return Location
                .builder()
                .name(location.getName())
                .locationType(locationType)
                .build();
    }

    public static LocationDTO convertEntityToDTO(Location location) {
        LocationTypeDTO locationType = LocationTypeMapper.convertEntityToDTO(location.getLocationType());

        return LocationDTO
                .builder()
                .id(location.getId())
                .name(location.getName())
                .locationType(locationType)
                .build();
    }

    public static LocationDTO convertExternalAddressToDTO(String name){
        return LocationDTO.builder().name(name).build();
    }
}
