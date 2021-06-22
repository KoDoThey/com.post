package com.vcc.internship.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class IndexController {
    @GET
    @Produces("text/plain")
    public String index() {
        return "Hello world!";
    }

    @GET
    @Path("/ping")
    @Produces("text/plain")
    public Response ping(@QueryParam("query") String query) {
        return Response.ok().status(200).type(MediaType.APPLICATION_JSON).entity("Hello, ping success!").build();
    }
}
