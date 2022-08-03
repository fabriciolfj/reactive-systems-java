package com.github.fabriciolfj.clienthttp;

import com.github.fabriciolfj.Person;
import com.github.fabriciolfj.UUID;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.time.temporal.ChronoUnit;

@RegisterRestClient(configKey = "httpbin")
public interface HttpBinService {

    @GET
    @Path("/uuid")
    //@Retry(maxRetries = 3, delay = 10000, jitter = 100)
    //@Timeout(value = 3, unit = ChronoUnit.SECONDS)
    //@Fallback(value =  MyFallback.class)
    @CircuitBreaker(
            delayUnit = ChronoUnit.SECONDS,
            //tempo para mudar para semi aberto
            delay = 10,
            //quantidade de requisições com sucesso durante a janela semi-aberta, para fechar o circuito
            successThreshold = 2,
            //percentual de requisições com base no volume informado abaixo, para abre o circuito
            failureRatio = 0.75,
            requestVolumeThreshold = 10
    )
    @Bulkhead(value = 2, waitingTaskQueue = 10000)
    Uni<UUID> getUUID();
    //Uni<Response> getUUID();

    class MyFallback implements FallbackHandler<Uni<UUID>> {

        @Override
        public Uni<UUID> handle(ExecutionContext executionContext) {
            System.out.println(executionContext.getFailure());
            var u = new UUID();
            u.uuid = java.util.UUID.randomUUID().toString();
            return Uni.createFrom().item(u);
        }
    }

    @POST
    @Path("/anything")
    String anything(Person person);


    @POST
    @Path("/anything")
    @ClientHeaderParam(name = "x-header", value = "constant value")
    String anythingWithConstantHeader(Person person);

    @POST
    @Path("/anything")
    String anythingWithHeader(Person person, @HeaderParam("x-header") String value);

    @POST
    @Path("/anything")
    String anythingWithQuery(Person person, @QueryParam("param1") String p1, @QueryParam("param2") String p2);
}
