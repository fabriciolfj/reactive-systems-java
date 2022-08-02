package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.entity.PersonEntity;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/upload")
public class HttpEndpoint {

    @Channel("upload")
    MutinyEmitter<PersonEntity> emitter;

    @POST
    public Uni<Response> updaload(final PersonEntity person) {
        return emitter.send(person)
                .invoke(() -> System.out.println("item enviado"))
                .replaceWith(Response.accepted().build())
                .onFailure()
                .recoverWithItem(t -> Response.status(Response.Status.BAD_REQUEST)
                        .entity(t.getMessage()).build());
    }

    @GET
    public Uni<List<PersonEntity>> getAll() {
        return PersonEntity.listAll();
    }
}
