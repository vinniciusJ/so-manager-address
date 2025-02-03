package br.unioeste.esi.so_manager_address.mappers;

import br.unioeste.esi.so_manager_address.domains.dto.*;
import br.unioeste.esi.so_manager_address.domains.entity.Address;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;

public class AddressMapper {
    private AddressMapper() {}

    public static AddressDTO convertEntityToDTO(Address address) {
        NeighborhoodDTO neighborhood = NeighborhoodMapper.convertEntityToDTO(address.getNeighborhood());
        LocationDTO location = LocationMapper.convertEntityToDTO(address.getLocation());
        CityDTO city = CityMapper.convertEntityToDTO(address.getCity());

        return AddressDTO
                .builder()
                .id(address.getId())
                .zipCode(address.getZipCode())
                .city(city)
                .location(location)
                .neighborhood(neighborhood)
                .build();
    }

    public static Address convertDTOToEntity(AddressDTO address) {
        Neighborhood neighborhood = NeighborhoodMapper.convertDTOToEntity(address.getNeighborhood());
        Location location = LocationMapper.convertDTOToEntity(address.getLocation());
        City city = CityMapper.convertDTOToEntity(address.getCity());

        return Address
                .builder()
                .zipCode(address.getZipCode())
                .city(city)
                .location(location)
                .neighborhood(neighborhood)
                .build();
    }
}
