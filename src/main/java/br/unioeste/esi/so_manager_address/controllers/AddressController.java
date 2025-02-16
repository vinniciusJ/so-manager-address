package br.unioeste.esi.so_manager_address.controllers;

import br.unioeste.esi.so_manager_address.services.AddressService;
import br.unioeste.esi.so_manager_address.services.CityService;
import br.unioeste.esi.so_manager_address.services.LocationService;
import br.unioeste.esi.so_manager_address.services.NeighborhoodService;
import br.unioste.esi.so_manager.address_lib.domains.dtos.AddressDTO;
import br.unioste.esi.so_manager.address_lib.domains.dtos.filters.AddressFiltersDTO;
import br.unioste.esi.so_manager.address_lib.domains.entities.Address;
import br.unioste.esi.so_manager.address_lib.domains.entities.City;
import br.unioste.esi.so_manager.address_lib.domains.entities.Location;
import br.unioste.esi.so_manager.address_lib.domains.entities.Neighborhood;
import br.unioste.esi.so_manager.address_lib.mappers.AddressMapper;
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
    public ResponseEntity<List<AddressDTO>> findAll(AddressFiltersDTO filters){
        List<AddressDTO> addresses = addressService.findAll(filters).stream().map(AddressMapper::convertEntityToDTO).toList();

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

    @GetMapping("/external")
    public ResponseEntity<AddressDTO> findExternalAddressByZipCode(@RequestParam String zipCode){
        AddressDTO address = addressService.findExternalAddressByZipCode(zipCode);

        return ResponseEntity.ok(address);
    }
}
