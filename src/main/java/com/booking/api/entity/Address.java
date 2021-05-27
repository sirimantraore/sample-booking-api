package com.booking.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ref;
    private String streetName;

    @Size(min = 1)
    private String city;
    @Size(min = 5, max = 5, message = "Postal code must be 5 characters")
    private String postalCode;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "city_id", referencedColumnName = "id")
    //@JsonManagedReference
    //private City city;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Apartment apartment;

    protected Address() {
        this.ref = UUID.randomUUID().toString();
    }
}
