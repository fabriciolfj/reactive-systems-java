package com.github.fabriciolfj.message;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class StreamingExample {

    @Outgoing("ticks")
    public Multi<Long> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop();
    }

    @Incoming("ticks")
    @Outgoing("groups5")
    public Multi<List<String>> group(final Multi<Long> stream) {
        return stream.onItem().transform(l -> Long.toString(l))
                .group().intoLists().of(5);
    }

    @Incoming("groups5")
    @Outgoing("hello3")
    public Multi<String> processGroup(final Multi<List<String>> list) {
        return list.onItem()
                .transform(h -> "Hello " + String.join(",", h));
        //return "Hello " + String.join(",", list);
    }

    @Outgoing("test")
    @Incoming("hello3")
    public Multi<String> addTest(final Multi<String> msg) {
        return msg.onItem().transform(s -> "Test " + s);
    }

    @Incoming("test")
    public void printTest(final String msg) {
        //msg.subscribe().with(c -> System.out.println(c));
        System.out.println(msg);
    }


}
