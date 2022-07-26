package com.github.fabriciolfj.message;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class RetryExample {

    @Outgoing("ticks")
    public Multi<Long> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow()
                .drop();
    }

    @Incoming("ticks")
    @Outgoing("Hello")
    @Retry(maxRetries = 10, delay = 1, delayUnit = ChronoUnit.SECONDS)
    public String hello(final Long id) {
        if (id == 3L) {
            throw new IllegalArgumentException();
        }

        return id.toString();
    }

    @Incoming("Hello")
    public void print(final String msg) {
        System.out.println(msg);
    }
}
