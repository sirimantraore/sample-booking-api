package com.booking.api.resources;

import com.booking.api.entity.Apartment;
import com.booking.api.repository.ApartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentResource.class);

    @Autowired
    private ApartmentRepository repository;

    @PostMapping
    public List<Apartment> createApartements(@RequestBody @NotNull List<Apartment> apartments) {
        LOGGER.info("createApartements for {} apartments", apartments.size());
        return repository.saveAll(apartments);
    }

    @GetMapping("/{reference}")
    public Apartment getApartmentByReference(@PathVariable @NotNull String reference) {
        LOGGER.info("getApartmentByReference for reference : {}", reference);
        return repository.findByRef(reference);
    }

    @GetMapping
    public List<Apartment> getAllApartments() {
        LOGGER.info("getAllApartments");
        return repository.findAll();
    }
}
