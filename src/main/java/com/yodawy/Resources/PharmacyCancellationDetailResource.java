package com.yodawy.Resources;

import com.yodawy.Models.PharmacyCancellationDetail;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;


@Path("/pharmacy_cancellation_details")
public class PharmacyCancellationDetailResource {

    @GET
    @Path("/get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<PharmacyCancellationDetail>> getAll() {
        return PharmacyCancellationDetail.listAll();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PharmacyCancellationDetail> getByID(@PathParam("id") Long id) {
        return PharmacyCancellationDetail.findById(id);

    }

    @POST
    @WithTransaction
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(PharmacyCancellationDetail entity) {
        return Panache.withTransaction(entity::persist)
        .replaceWith(Response.ok(entity).status(Response.Status.CREATED)::build);
    }

     @POST
     @WithTransaction
     @Path("/create_html")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public Uni<Response> create_pharmacy_html(@BeanParam PharmacyCancellationDetail.CreationParameters params) {
         PharmacyCancellationDetail new_entry = new PharmacyCancellationDetail(params);
         return Panache.withTransaction(new_entry::persist)
         .replaceWith(Response.ok(new_entry).status(Response.Status.CREATED)::build);
     }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> PharmacyCancellationDetail.deleteById(id))
        .map(deleted -> deleted
        ? Response.ok().status(Response.Status.NO_CONTENT).build()
        : Response.ok(Response.Status.NOT_FOUND).build());
    }

}
