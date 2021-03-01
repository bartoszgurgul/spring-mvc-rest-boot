package pl.javastart.springmvcrestboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.javastart.springmvcrestboot.model.City;

@RestController
public class CityController {
    @RequestMapping("/city")
    public City getCity(){
        return new City("Warsawa", 1_700_000);
    }
}
