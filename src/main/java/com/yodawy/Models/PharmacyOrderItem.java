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

import java.util.Random;

@Entity
@Table(name = "pharmacy_order_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrderItem extends PanacheEntity {

    String name;
    Double list_price;
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
        public Double list_price;
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

    public PharmacyOrderItem(Faker faker, Long order_id) {
        Random rand = new Random();
        String[] forms = {"Tablets", "Flex-pen", "Suppository"};
        String[] units = {"Pill", "Bottle", "Sachet"};

        name = faker.medical().medicineName();
        list_price = faker.number().randomDouble(2,1,50);
        quantity = faker.number().numberBetween(1, 6);
        image_url = faker.internet().url();
        form = forms[rand.nextInt(forms.length)];
        form = units[rand.nextInt(units.length)];

        this.order_id = order_id;
    }
}
