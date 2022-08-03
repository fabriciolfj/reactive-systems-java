package com.github.fabriciolfj.clienthttp;

/*
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WebClientExample {

    private final WebClient webClient;

    @Inject
    public WebClientExample(Vertx vertx) {
        this.webClient = WebClient.create(vertx);
    }

    @PreDestroy
    public void close() {
        webClient.close();
    }

    public Uni<JsonObject> invokeService() {
        return webClient
                .getAbs("https://httpbin.org/json").send()
                .onItem().transform(response -> {
                    if (response.statusCode() == 200) {
                        return response.bodyAsJsonObject();
                    } else {
                        return new JsonObject()
                                .put("error", response.statusMessage());
                    }
                });
    }
}*/
