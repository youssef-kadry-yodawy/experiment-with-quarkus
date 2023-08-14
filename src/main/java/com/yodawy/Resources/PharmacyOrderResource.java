package com.yodawy.Resources;

import com.yodawy.Models.PharmacyOrder;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;


@Path("/pharmacy_orders")
public class PharmacyOrderResource {

    @GET
    @Path("/get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PharmacyOrder>> getAll() {
        return PharmacyOrder.listAll();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PharmacyOrder> getByID(@PathParam("id") Long id) {
        return PharmacyOrder.findById(id);

    }

    @POST
    @WithTransaction
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(PharmacyOrder entity) {
        return Panache.withTransaction(entity::persist)
        .replaceWith(Response.ok(entity).status(Response.Status.CREATED)::build);
    }

     @POST
     @WithTransaction
     @Path("/create_html")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public Uni<Response> create_pharmacy_html(@BeanParam PharmacyOrder.CreationParameters params) {
         PharmacyOrder new_entry = new PharmacyOrder(params);
         return Panache.withTransaction(new_entry::persist)
         .replaceWith(Response.ok(new_entry).status(Response.Status.CREATED)::build);
     }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> PharmacyOrder.deleteById(id))
        .map(deleted -> deleted
        ? Response.ok().status(Response.Status.NO_CONTENT).build()
        : Response.ok(Response.Status.NOT_FOUND).build());
    }

}
