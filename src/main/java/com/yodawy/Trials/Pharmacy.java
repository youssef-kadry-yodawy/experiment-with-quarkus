package com.yodawy.Trials;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Pharmacy")
@Table(name = "pharmacies")
public class Pharmacy extends PanacheEntity{

    public String name;

    public String address;
}
