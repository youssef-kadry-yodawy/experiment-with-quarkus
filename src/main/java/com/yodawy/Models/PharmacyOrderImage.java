package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.javafaker.Faker;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jboss.resteasy.reactive.RestForm;

@Entity
@Table(name = "pharmacy_order_images")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderImage extends PanacheEntity {

    String image_url;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    public PharmacyOrder pharmacy_order;
    public Long order_id;

    public static class CreationParameters {
        @RestForm
        public String image_url;

        @RestForm
        Long order_id;
    }

    public PharmacyOrderImage() {}

    public PharmacyOrderImage(CreationParameters params) {
        image_url = params.image_url;

        order_id = params.order_id;
    }

    public PharmacyOrderImage(Faker faker, Long order_id) {
        image_url = faker.internet().image();

        this.order_id = order_id;
    }
}
