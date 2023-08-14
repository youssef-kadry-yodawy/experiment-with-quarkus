package com.yodawy.General;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.github.javafaker.Faker;
import com.yodawy.Models.PharmacyOrder;
import com.yodawy.Models.PharmacyOrderUser;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Path("/database-seeder")
public class DatabaseSeeder {

    public static Faker faker = new Faker();

    @GET
    @Path("/seed")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<PharmacyOrderUser> seed() {

        Uni<PharmacyOrderUser> users = Uni.createFrom().item(new PharmacyOrderUser());

        for (int i = 0; i < 5; i++) {
            users = users.onItem().transformToUni(ignore -> {
                PharmacyOrderUser user = new PharmacyOrderUser();
                user.user_global_id = faker.idNumber().valid();
                user.name = faker.name().fullName();
                user.mobile_number = faker.phoneNumber().cellPhone();
                user.age = faker.number().numberBetween(18, 80);
                return Panache.withTransaction(user::persist);
            });
        }

        return users;
    }
}