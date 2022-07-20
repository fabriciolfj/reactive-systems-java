package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.message.ProducerService;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api/v1/persons")
public class PersonController {

    @Inject
    private ProducerService producerService;

    @GET
    @Path("{name}")
    public Uni<Response> sendName(@RestPath("name") final String name) {
        return producerService.createMessage(name)
                .onItem().transform(r -> Response.accepted().build());
    }
}
