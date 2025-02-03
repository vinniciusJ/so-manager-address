package br.unioeste.esi.so_manager_address.services;

import br.unioeste.esi.so_manager_address.domains.dto.CityDTO;
import br.unioeste.esi.so_manager_address.domains.entity.City;
import br.unioeste.esi.so_manager_address.domains.entity.State;
import br.unioeste.esi.so_manager_address.exceptions.AddressException;
import br.unioeste.esi.so_manager_address.mappers.CityMapper;
import br.unioeste.esi.so_manager_address.mappers.StateMapper;
import br.unioeste.esi.so_manager_address.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City create(CityDTO form, State state) {
        City city = CityMapper.convertDTOToEntity(form);

        city.setState(state);

        return cityRepository.save(city);
    }

    public List<City> findAll(){
        return cityRepository.findAll();
    }

    public City update(Long id, CityDTO form) {
        City city = findById(id);
        State state = StateMapper.convertDTOToEntity(form.getState());

        city.setName(form.getName());
        city.setState(state);

        return cityRepository.save(city);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    public URI createURI(UriComponentsBuilder builder, City city) {
        return builder.path("/city/{id}").buildAndExpand(city.getId()).toUri();
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new AddressException(HttpStatus.NOT_FOUND, "Cidade não encontrada com ID " + id)
        );
    }
}
