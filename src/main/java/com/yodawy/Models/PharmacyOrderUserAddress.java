package com.yodawy.Models;


// import io.quarkus.hibernate.orm.PersistenceUnit.List;
import com.fasterxml.jackson.annotation.*;
import com.github.javafaker.Faker;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "pharmacy_order_user_addresses")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderUserAddress extends PanacheEntity{

    public String address_global_id;
    public String building_number;
    public String street_name;
    public String city;
    public String area;
    public String type;
    public Integer floor;
    public Integer apartment;
    public String landmark;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public PharmacyOrderUser pharmacy_order_user;
    public Long user_id;

    @OneToMany(
            mappedBy = "pharmacy_order_user_address",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrder> pharmacy_orders;

    public static class CreationParameters {
        @RestForm
        public String address_global_id;
        @RestForm
        public String building_number;
        @RestForm
        public String street_name;
        @RestForm
        public String city;
        @RestForm
        public String area;
        @RestForm
        public String type;
        @RestForm
        public Integer floor;
        @RestForm
        public Integer apartment;
        @RestForm
        public String landmark;

        @RestForm
        Long user_id;
    }

    public PharmacyOrderUserAddress() {}

    public PharmacyOrderUserAddress(CreationParameters params) {
        address_global_id = params.address_global_id;
        building_number = params.building_number;
        street_name = params.street_name;
        city = params.city;
        area = params.area;
        type = params.type;
        floor = params.floor;
        apartment = params.apartment;
        landmark = params.landmark;

        user_id = params.user_id;
    }

    public PharmacyOrderUserAddress(Faker faker, PharmacyOrderUser user) {
        address_global_id = faker.idNumber().invalid();
        building_number = faker.address().buildingNumber();
        street_name = faker.address().streetName();
        city = faker.address().city();
        area = faker.address().state();
        String[] types = {"Home", "Office", "Other"};
        type = types[new Random().nextInt(types.length)];
        floor = faker.number().numberBetween(0, 20);
        apartment = faker.number().numberBetween(1, 80);
        landmark = faker.university().name();

        user_id = user.id;
    }
}
