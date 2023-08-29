package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
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
            orphanRemoval = true
    )
    @JsonIgnore
    public List<PharmacyOrder> pharmacy_orders;

    public static class CreationParameters {
        @RestForm
        public String name;
    }

    public PharmacyOrderStatus() {}

    public PharmacyOrderStatus(CreationParameters params) {
        name = params.name;
    }

    public PharmacyOrderStatus(String name) {
        this.name = name;
    }

    public static Uni<PharmacyOrderStatus> initializePharmacyOrderStatuses() {
        String[] statuses = {"Received", "Approved", "Rejected", "Cancelled", "Out For Delivery", "Delivered"};

        Uni<PharmacyOrderStatus> train = Uni.createFrom().nullItem();
        for (String status : statuses) {
            train = train.replaceWith(new PharmacyOrderStatus(status).persist());
        }

        return train;
    }
}
