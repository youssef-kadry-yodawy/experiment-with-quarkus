package com.yodawy.Resources;

import com.yodawy.Models.PharmacyOrderUser;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;



@Path("/pharmacy_order_users")
public class PharmacyOrderUserResource {

    @GET
    @Path("/get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PharmacyOrderUser>> getAll() {
        return PharmacyOrderUser.listAll();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PharmacyOrderUser> getByID(@PathParam("id") Long id) {
        return PharmacyOrderUser.findById(id);

    }

    @POST
    @WithTransaction
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(PharmacyOrderUser entity) {
        return Panache.withTransaction(entity::persist)
        .replaceWith(Response.ok(entity).status(Response.Status.CREATED)::build);
    }

     @POST
     @WithTransaction
     @Path("/create_html")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public Uni<Response> create_pharmacy_html(@BeanParam PharmacyOrderUser.CreationParameters params) {
         PharmacyOrderUser new_entry = new PharmacyOrderUser(params);
         return Panache.withTransaction(new_entry::persist)
         .replaceWith(Response.ok(new_entry).status(Response.Status.CREATED)::build);
     }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> PharmacyOrderUser.deleteById(id))
        .map(deleted -> deleted
        ? Response.ok().status(Response.Status.NO_CONTENT).build()
        : Response.ok(Response.Status.NOT_FOUND).build());
    }

}
