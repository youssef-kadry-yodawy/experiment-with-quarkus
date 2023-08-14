package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.jboss.resteasy.reactive.RestForm;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pharmacy_orders")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyOrder extends PanacheEntity {

    String order_number;
    Float total_list_price;
    Float total_discount;
    Float delivery_charge;
    Float net_amount;
    String order_notes;
    Integer payment_method_id;
    LocalDateTime accepted_at;
    LocalDateTime assigned_at;

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

    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrderItem> order_items;

    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyOrderImage> order_images;

    @OneToMany(
            mappedBy = "pharmacy_order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<PharmacyCancellationDetail> cancellation_details;

    public static class CreationParameters {
        @RestForm
        public String order_number;
        @RestForm
        public Float total_list_price;
        @RestForm
        public Float total_discount;
        @RestForm
        public Float delivery_charge;
        @RestForm
        public Float net_amount;
        @RestForm
        public String order_notes;
        @RestForm
        public Integer payment_method_id;
        @RestForm
        public LocalDateTime accepted_at;
        @RestForm
        public LocalDateTime assigned_at;

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
}
