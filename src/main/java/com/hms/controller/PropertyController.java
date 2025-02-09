package com.hms.controller;

import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {
    @Autowired
    private PropertyRepository propertyRepository;


    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchHotels(String cityName){
        List<Property> properties = propertyRepository.searchHotels(cityName);
        return new ResponseEntity<>(properties, HttpStatus.OK);

    }
}
