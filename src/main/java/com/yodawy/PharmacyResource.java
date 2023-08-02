package com.yodawy;

import java.util.List;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;

import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@WithSession
@Path("/pharmacies")
public class PharmacyResource {

    @Inject
    Template pharmacy_creation;

    @GET
    @Path("/display_all")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> display_all_pharmacies() {

        Uni<List<Pharmacy>> pharmacies = Pharmacy.listAll();

        return pharmacies.onItem().transformToUni(
            p ->  Uni.createFrom().item(pharmacy_creation.data("pharmacies", pharmacies))
            );
    }
    
    @GET
    @Path("/get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Pharmacy>> getAll() {
        return Pharmacy.listAll();
    }

    @GET
    @Path("/by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Pharmacy> getByID(@PathParam("id") Long id) {
        return Pharmacy.findById(id);
            
    }

    @POST
    @WithTransaction
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create_pharmacy(Pharmacy pharmacy) {
        return Panache.withTransaction(pharmacy::persist)
        .replaceWith(Response.ok(pharmacy).status(Response.Status.CREATED)::build);
    }

    public static class Parameters {
        @RestForm
        String name;
        @RestForm
        String address;
    }

    @POST
    @WithTransaction
    @Path("/create_html")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> create_pharmacy_html(@BeanParam Parameters param) {
        Pharmacy pharma = new Pharmacy();
        pharma.name = param.name;
        pharma.address = param.address;
        return Panache.withTransaction(pharma::persist)
        .replaceWith(Response.ok(pharma).status(Response.Status.CREATED)::build);
    }

    @DELETE
    @Path("/delete_pharmacy/{id}")
    public Uni<Response> delete_pharmacy(@RestPath Long id) {
        return Panache.withTransaction(() -> Pharmacy.deleteById(id))
        .map(deleted -> deleted 
        ? Response.ok().status(Response.Status.NO_CONTENT).build() 
        : Response.ok(Response.Status.NOT_FOUND).build());
    }

}
