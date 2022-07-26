package com.github.fabriciolfj;

import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyMessage implements Message<String> {

    private final String payload;

    public MyMessage(final String payload) {
        this.payload = payload;
    }

    public MyMessage(final long id) {
        this(Long.toString(id));
    }

    @Override
    public String getPayload() {
        return this.payload;
    }

    @Override
    public Supplier<CompletionStage<Void>> getAck() {
        return () -> {
            System.out.println("Ack for " + payload);
            return CompletableFuture.completedFuture(null);
        };
    }

    @Override
    public Function<Throwable, CompletionStage<Void>> getNack() {
        return reason -> {
            System.out.println("Negative ack for " + reason);
            return CompletableFuture.completedFuture(null);
        };
    }
}
