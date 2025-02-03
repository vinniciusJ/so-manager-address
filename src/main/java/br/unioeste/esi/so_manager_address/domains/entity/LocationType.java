package br.unioeste.esi.so_manager_address.domains.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location_type")
@NoArgsConstructor @AllArgsConstructor
@Data @Builder
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}