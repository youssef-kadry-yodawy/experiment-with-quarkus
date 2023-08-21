package com.yodawy.Trials;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Dependent
@SessionScoped
public class EntityResourceBase<EntityClass extends PanacheEntity> {

    @GET
    @Path("/get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<EntityClass>> getAll() {
        return EntityClass.listAll();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<EntityClass> getByID(@PathParam("id") Long id) {
        return EntityClass.findById(id);

    }

    @POST
    @WithTransaction
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(EntityClass entity) {
        return Panache.withTransaction(entity::persist)
        .replaceWith(Response.ok(entity).status(Response.Status.CREATED)::build);
    }

    @DELETE
    @Path("/delete/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> EntityClass.deleteById(id))
        .map(deleted -> deleted
        ? Response.ok().status(Response.Status.NO_CONTENT).build()
        : Response.ok(Response.Status.NOT_FOUND).build());
    }

}
