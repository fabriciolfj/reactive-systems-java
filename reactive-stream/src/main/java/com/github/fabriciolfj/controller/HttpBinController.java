package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.UUID;
import com.github.fabriciolfj.clienthttp.HttpBinService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api/v1/httpbin")
public class HttpBinController {

    @RestClient
    @Inject
    private HttpBinService service;

    @GET
    public Uni<UUID> get() {
        return service.getUUID().invoke(r -> System.out.println("Servico externo chamado"));
    }
}
