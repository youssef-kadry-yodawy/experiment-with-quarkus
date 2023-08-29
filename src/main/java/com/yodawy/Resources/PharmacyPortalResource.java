package com.yodawy.Resources;

import java.util.List;

import org.hibernate.reactive.mutiny.Mutiny;

import com.yodawy.Models.PharmacyOrder;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pharmacy_portal")
public class PharmacyPortalResource {

    @Inject
    Template pharmacy_portal;

    @GET
    @Path("/world")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello world!";
    }

    @GET
    @Path("/portal")
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> pharmacyPortal() {
        Uni<List<PharmacyOrder>> orders= PharmacyOrder.list("status_id = 1 or status_id = 2 or status_id = 5");

        return pharmacy_portal.data(
            "orders", orders,
            "active", "selected",
            "history", "unselected",
            "selected_order", null)
            .createUni();
    }

    @GET
    @Path("/select_active_orders")
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> selectActiveOrders() {
        Uni<List<PharmacyOrder>> orders= PharmacyOrder.list("status_id = 1 or status_id = 2 or status_id = 5");

        return pharmacy_portal.getFragment("order_selection_block").data(
            "orders", orders,
            "active", "selected",
            "history", "unselected")
            .createUni();
    }

    @GET
    @Path("/select_history")
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> selectHistory() {
        Uni<List<PharmacyOrder>> orders= PharmacyOrder.list("status_id = 3 or status_id = 4 or status_id = 6");

        return Uni.createFrom().completionStage( pharmacy_portal.getFragment("order_selection_block").data(
            "orders", orders,
            "active", "unselected",
            "history", "selected").renderAsync());
    }

    @GET
    @Path("/get_order/{id}")
    @Produces(MediaType.TEXT_HTML)
    @WithSession
    public Uni<String> getOrder(@PathParam("id") Long id) {
        Uni<PharmacyOrder> order = PharmacyOrder.findById(id);
        return order.chain(selected_order -> pharmacy_portal.getFragment("order_details_block").data(
            "selected_order", selected_order,
            "selected_order_items", Mutiny.fetch(selected_order.order_items)).createUni());
    }


}