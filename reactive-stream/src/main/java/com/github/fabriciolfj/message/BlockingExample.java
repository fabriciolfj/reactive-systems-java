package com.github.fabriciolfj.message;

import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlockingExample {

    /*@Incoming("ticks2")
    @Outgoing("hello2")
    @Blocking
    public String hello(long tick) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello " + tick;
    }*/
}
