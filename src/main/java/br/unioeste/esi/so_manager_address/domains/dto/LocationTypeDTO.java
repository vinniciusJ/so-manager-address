package br.unioeste.esi.so_manager_address.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
public class LocationTypeDTO {
    private Long id;
    private String name;
}
