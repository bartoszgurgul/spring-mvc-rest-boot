package pl.javastart.springmvcrestboot.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.javastart.springmvcrestboot.model.City;

import java.awt.*;
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

    @GetMapping("{id}")
    public City getCity(@PathVariable int id){
        return cities.get(id);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void save(@RequestBody City city){
        cities.add(city);
    }
}
