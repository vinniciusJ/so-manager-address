package br.unioeste.esi.so_manager_address.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class AddressDTO {
    private Long id;
    private String zipCode;
    private NeighborhoodDTO neighborhood;
    private LocationDTO location;
    private CityDTO city;
}
