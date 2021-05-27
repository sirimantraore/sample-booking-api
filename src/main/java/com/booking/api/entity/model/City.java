package com.booking.api.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class City {

    private Long id;
    private String ref;
    private String name;
    private String postalCode;

    public City(String name, String postalCode) {
        this.ref = UUID.randomUUID().toString();
        this.name = name;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return String.format(
                "City[id=%d, ref='%s', name='%s', postalCode='%s']",
                id, ref, name, postalCode);
    }
}
