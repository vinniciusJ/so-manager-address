package br.unioeste.esi.so_manager_address.domains.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class State {
    @Id
    private String abbreviation;
    private String name;
}
