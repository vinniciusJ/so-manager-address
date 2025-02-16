package br.unioeste.esi.so_manager_address.configs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {
        "br.unioeste.esi.so_manager_address.domains.entities",
        "br.unioste.esi.so_manager.address_lib.domains.entities"
})
@EnableJpaRepositories(basePackages = {"br.unioeste.esi.so_manager_address.repositories" })
public class JpaConfig {
}