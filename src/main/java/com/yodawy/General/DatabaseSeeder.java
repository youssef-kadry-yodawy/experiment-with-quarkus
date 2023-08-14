package com.yodawy.General;

import com.github.javafaker.Faker;
import com.yodawy.Models.*;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
@Path("/database-seeder")
public class DatabaseSeeder {

    public static Faker faker = new Faker();

    @GET
    @Path("/seed")
    @Produces(MediaType.APPLICATION_JSON)
    @WithTransaction
    public Uni<Response> seed() {

        Uni<PanacheEntityBase> train = Uni.createFrom().nullItem();

        // Only initialize the status table if it's not initialized
        train = train.replaceWith(PharmacyOrderStatus.count()).chain(count -> {
                if (count == 0) {
                    return PharmacyOrderStatus.initializePharmacyOrderStatuses();
                }
                return Uni.createFrom().nullItem();
        });

        train = train.replaceWith(Uni.createFrom().nullItem());
        // Create 5 users and an address corresponding to each one
        for (int i = 0; i < 5; i++) {
            // Create 5 users
            train = train.replaceWith(new PharmacyOrderUser(faker).persist());

            // Create their corresponding addresses
            train = train.chain(user -> new PharmacyOrderUserAddress(faker, (PharmacyOrderUser) user).persist());

            // Create the associated pharmacy orders
            train = train.chain(address -> {

                        Uni<PanacheEntityBase> order_train = Uni.createFrom().nullItem();
                        for (int j = 0; j < 5; j++){

                            // Create 5 orders for each address
                            order_train = order_train.replaceWith(new PharmacyOrder(faker, ((PharmacyOrderUserAddress)address).user_id, ((PharmacyOrderUserAddress)address).id).persist());

                            // Create the associated order items
                            order_train = order_train.chain(order -> {

                                // Define the Order Item Train
                                Uni<PanacheEntity> order_item_train = Uni.createFrom().nullItem();
                                for (int k = 0; k < 5; k++){

                                    // Create 5 order items for each order
                                    order_item_train = order_item_train.replaceWith(new PharmacyOrderItem(faker, ((PharmacyOrder)order).id).persist());
                                }

                                // Define the Order Image Train
                                Uni<PanacheEntity> order_image_train = Uni.createFrom().nullItem();
                                for (int k = 0; k < 5; k++){

                                    // Create 5 order images for each order
                                    order_image_train = order_image_train.replaceWith(new PharmacyOrderImage(faker, ((PharmacyOrder)order).id).persist());
                                }

                                // Join the two trains, so we can finally get out of here
                                return Uni.join().all(order_item_train, order_image_train).andCollectFailures().replaceWith(Uni.createFrom().nullItem()); // Transform to null, so it can be easily cast and carried on

                            });

                        }
                        return order_train;

                    }).replaceWith(Uni.createFrom().nullItem()); // Transform to null, so it can be easily cast and carried on
        }
        return train.replaceWith(Response.ok()::build);
    }
}