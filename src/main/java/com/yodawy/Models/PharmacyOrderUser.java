package com.yodawy.Models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;
import org.jboss.resteasy.reactive.RestForm;

@Entity
@Table(name = "pharmacy_order_users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderUser extends PanacheEntity{

    public String user_global_id;
    public String name;
    public String mobile_number;
    public int age;
    @OneToMany(
            mappedBy = "pharmacy_order_user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrderUserAddress> pharmacy_order_user_addresses;

    @OneToMany(
            mappedBy = "pharmacy_order_user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrder> pharmacy_orders;

    public static class CreationParameters {
        @RestForm
        String user_global_id;
        @RestForm
        String name;
        @RestForm
        String mobile_number;
        @RestForm
        Integer age;
    }

    public PharmacyOrderUser() {}

    public PharmacyOrderUser(CreationParameters params) {
        user_global_id = params.user_global_id;
        name = params.name;
        mobile_number = params.mobile_number;
        age = params.age;
    }

}
