package pl.javastart.springmvcrestboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.springmvcrestboot.model.City;

import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    List<City> cities;

    public CityController(){
        cities = Collections.synchronizedList(new ArrayList<>());
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<City> getCities(@RequestParam(defaultValue = "name") String orderBy){
        List<City> citiesCopy = new ArrayList<>(cities);
        if ("name".equals(orderBy)){
            citiesCopy.sort(Comparator.comparing(City::getName));
        } else if ("population".equals(orderBy)){
            citiesCopy.sort(Comparator.comparing(City::getPopulation));
        }
        return citiesCopy;
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable int id) {

        if (id >= cities.size() || id < 0)
            return ResponseEntity.notFound().build();
        else {
            City city = cities.get(id);
            return ResponseEntity.ok(city);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<City> save(@RequestBody City city){
        if (!cities.contains(city)){
            cities.add(city);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(cities.size() - 1)
                    .toUri();
            return ResponseEntity.created(location).body(city);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
