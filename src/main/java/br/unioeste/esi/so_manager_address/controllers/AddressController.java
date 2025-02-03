package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.domains.dto.AddressDTO;
import br.unioeste.esi.so_manager_address.domains.entity.Address;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.Location;
import br.unioeste.esi.so_manager_address.domains.entity.Neighborhood;
import br.unioeste.esi.so_manager_address.mappers.AddressMapper;
import br.unioeste.esi.so_manager_address.services.AddressService;
import br.unioeste.esi.so_manager_address.services.CityService;
import br.unioeste.esi.so_manager_address.services.LocationService;
import br.unioeste.esi.so_manager_address.services.NeighborhoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final NeighborhoodService neighborhoodService;
    private final LocationService locationService;
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll(){
        List<AddressDTO> addresses = addressService.findAll().stream().map(AddressMapper::convertEntityToDTO).toList();

        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody AddressDTO form, UriComponentsBuilder uriBuilder){
        Neighborhood neighborhood = neighborhoodService.findById(form.getNeighborhood().getId());
        Location location = locationService.findById(form.getLocation().getId());
        City city = cityService.findById(form.getCity().getId());

        Address address = addressService.create(form, neighborhood, location, city);

        URI uri = addressService.createURI(uriBuilder, address);

        return ResponseEntity.created(uri).body(AddressMapper.convertEntityToDTO(address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id){
        Address address = addressService.findById(id);

        return ResponseEntity.ok(AddressMapper.convertEntityToDTO(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> save(@PathVariable Long id, @RequestBody AddressDTO form, UriComponentsBuilder uriBuilder){
        Neighborhood neighborhood = neighborhoodService.findById(form.getNeighborhood().getId());
        Location location = locationService.findById(form.getLocation().getId());
        City city = cityService.findById(form.getCity().getId());

        addressService.update(id, form, neighborhood, location, city);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
