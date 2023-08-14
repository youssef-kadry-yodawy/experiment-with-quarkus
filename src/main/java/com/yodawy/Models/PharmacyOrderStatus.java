package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;

@Entity
@Table(name = "pharmacy_order_statuses")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderStatus extends PanacheEntity {

    public String name;

    @OneToMany(
            mappedBy = "pharmacy_order_status",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrder> pharmacy_orders;

    public static class CreationParameters {
        @RestForm
        public String name;
    }

    public PharmacyOrderStatus() {}

    public PharmacyOrderStatus(CreationParameters params) {
        name = params.name;
    }
}
