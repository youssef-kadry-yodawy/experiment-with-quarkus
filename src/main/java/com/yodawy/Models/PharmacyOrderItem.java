package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jboss.resteasy.reactive.RestForm;

@Entity
@Table(name = "pharmacy_order_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderItem extends PanacheEntity {

    String name;
    Float list_price;
    Integer quantity;
    String image_url;
    String form;
    String unit;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    public PharmacyOrder pharmacy_order;
    public Long order_id;

    public static class CreationParameters {
        @RestForm
        public String name;
        @RestForm
        public Float list_price;
        @RestForm
        public Integer quantity;
        @RestForm
        public String image_url;
        @RestForm
        public String form;
        @RestForm
        public String unit;

        @RestForm
        Long order_id;
    }

    public PharmacyOrderItem() {}

    public PharmacyOrderItem(CreationParameters params) {
        name = params.name;
        list_price = params.list_price;
        quantity = params.quantity;
        image_url = params.image_url;
        form = params.form;
        unit = params.unit;

        order_id = params.order_id;
    }
}
