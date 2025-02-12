package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.FederalUnitDTO;
import br.unioeste.esi.so_manager_address.domains.dto.NeighborhoodDTO;
import br.unioeste.esi.so_manager_address.domains.entity.FederalUnit;

public class FederalUnitMapper {
    public static FederalUnitDTO convertEntityToDTO(FederalUnit federalUnit) {
        return FederalUnitDTO
                .builder()
                .name(federalUnit.getName())
                .abbreviation(federalUnit.getAbbreviation())
                .build();
    }

    public static FederalUnit convertDTOToEntity(FederalUnitDTO state) {
        return FederalUnit
                .builder()
                .name(state.getName())
                .abbreviation(state.getAbbreviation())
                .build();
    }

    public static FederalUnitDTO convertExternalAddressToDTO(String abbreviation, String name){
        return FederalUnitDTO.builder().name(name).abbreviation(abbreviation).build();
    }
}
