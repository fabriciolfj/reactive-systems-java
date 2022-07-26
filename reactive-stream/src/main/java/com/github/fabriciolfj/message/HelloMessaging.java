package com.github.fabriciolfj.message;

import com.github.fabriciolfj.MyMessage;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;

//@ApplicationScoped
public class HelloMessaging {
/*
   // @Outgoing("ticks")
    public Multi<MyMessage> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transform(MyMessage::new);
    }

    //@Incoming("ticks")
    //@Outgoing("hello")
    public Message<String> hello(final Message<String> tick) {
        return tick.withPayload("Hello " + tick.getPayload());
    }

    //@Incoming("hello")
    public void print(final String msg) {
        if (msg.contains("3")) {
            throw new IllegalArgumentException("boom");
        }

        System.out.println(msg);
    }*/
}
