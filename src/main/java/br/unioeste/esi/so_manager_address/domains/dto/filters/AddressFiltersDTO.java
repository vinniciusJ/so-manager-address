package br.unioeste.esi.so_manager_address.domains.dto.filters;

public record AddressFiltersDTO(
        String zipCode,
        String locationName,
        String neighborhoodName,
        String cityName,
        String federalUnitName
){

}