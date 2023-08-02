// package com.yodawy;

// import io.quarkus.qute.Template;
// import io.quarkus.qute.TemplateInstance;
// import jakarta.inject.Inject;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.QueryParam;
// import jakarta.ws.rs.core.MediaType;

// @Path("/hello")
// public class GreetingResource {

//     @Inject
//     Template hello;

//     @GET
//     @Produces(MediaType.TEXT_PLAIN)
//     public TemplateInstance get_hello(@QueryParam("name") String name) {
//         return hello.instance();
//     }
// }
