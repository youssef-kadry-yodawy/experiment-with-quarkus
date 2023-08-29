package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.javafaker.Faker;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.jboss.resteasy.reactive.RestForm;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "pharmacy_orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrder extends PanacheEntity {

    public String order_number;
    public Double total_list_price;
    public Double total_discount;
    public Double delivery_charge;
    public Double net_amount;
    public String order_notes;
    public Integer payment_method_id;
    public Date accepted_at;
    public Date assigned_at;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public PharmacyOrderUser pharmacy_order_user;
    public Long user_id;

    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    public PharmacyOrderUserAddress pharmacy_order_user_address;
    public Long address_id;

    @ManyToOne
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    public PharmacyOrderStatus pharmacy_order_status;
    public Long status_id;

    @JsonIgnore
    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PharmacyOrderItem> order_items;
    
    @JsonIgnore
    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PharmacyOrderImage> order_images;

    @JsonIgnore
    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PharmacyCancellationDetail> cancellation_details;

    public static class CreationParameters {
        @RestForm
        public String order_number;
        @RestForm
        public Double total_list_price;
        @RestForm
        public Double total_discount;
        @RestForm
        public Double delivery_charge;
        @RestForm
        public Double net_amount;
        @RestForm
        public String order_notes;
        @RestForm
        public Integer payment_method_id;
        @RestForm
        public Date accepted_at;
        @RestForm
        public Date assigned_at;

        @RestForm
        Long user_id;
        @RestForm
        Long address_id;
        @RestForm
        Long status_id;
    }

    public PharmacyOrder() {}

    public PharmacyOrder(CreationParameters params) {

        order_number = params.order_number;
        total_list_price = params.total_list_price;
        total_discount = params.total_discount;
        delivery_charge = params.delivery_charge;
        net_amount = params.net_amount;
        order_notes = params.order_notes;
        payment_method_id = params.payment_method_id;
        accepted_at = params.accepted_at;
        assigned_at = params.assigned_at;

        user_id = params.user_id;
        address_id = params.address_id;
        status_id = params.status_id;
    }

    public PharmacyOrder(Faker faker, Long user_id, Long address_id) {

        order_number = faker.number().digits(5);
        total_list_price = faker.number().randomDouble(2,1,500);
        total_discount = faker.number().randomDouble(2,0,1);
        delivery_charge = (double) faker.number().numberBetween(10, 20);
        net_amount = total_list_price * total_discount + delivery_charge;
        order_notes = faker.bothify("?".repeat(50));
        payment_method_id = faker.number().numberBetween(0, 2);
        accepted_at = faker.date().future(1, TimeUnit.HOURS);
        assigned_at = faker.date().future(1, TimeUnit.HOURS);

        this.user_id = user_id;
        this.address_id = address_id;
        this.status_id = (long) faker.number().numberBetween(1, 6);
    }
}
