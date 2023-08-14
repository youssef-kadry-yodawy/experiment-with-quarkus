package com.yodawy.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.jboss.resteasy.reactive.RestForm;

import java.time.LocalDateTime;

@Entity
@Table(name = "pharmacy_order_images")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PharmacyCancellationDetail extends PanacheEntity {

    String cancellation_note;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    public PharmacyOrder pharmacy_order;
    public Long order_id;

    public static class CreationParameters {
        @RestForm
        public String cancellation_note;

        @RestForm
        Long order_id;
    }

    public PharmacyCancellationDetail() {}

    public PharmacyCancellationDetail(CreationParameters params) {

        cancellation_note = params.cancellation_note;

        order_id = params.order_id;
    }

}
