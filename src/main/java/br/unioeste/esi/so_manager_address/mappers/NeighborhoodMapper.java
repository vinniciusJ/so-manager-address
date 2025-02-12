package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.NeighborhoodDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;

public class NeighborhoodMapper {
    private NeighborhoodMapper() {}

    public static Neighborhood convertDTOToEntity(NeighborhoodDTO neighborhood){
        return Neighborhood
                .builder()
                .name(neighborhood.getName())
                .build();
    }

    public static NeighborhoodDTO convertEntityToDTO(Neighborhood neighborhood){
        return NeighborhoodDTO
                .builder()
                .id(neighborhood.getId())
                .name(neighborhood.getName())
                .build();
    }

    public static NeighborhoodDTO convertExternalAddressToDTO(String name){
        return NeighborhoodDTO.builder().name(name).build();
    }
}
