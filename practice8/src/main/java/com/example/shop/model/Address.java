package com.example.shop.model;
import jakarta.persistence.Embeddable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    @Column(name = "address_city") // Явное указание
    private String city;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_zipcode")
    private String zipcode;
}

