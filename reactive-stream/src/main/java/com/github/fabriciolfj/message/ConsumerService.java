package com.github.fabriciolfj.message;

import com.github.fabriciolfj.Person;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsumerService {

    /*@Channel("my-channel")
    Multi<Person> streamOfPersons;

    void init() {
        streamOfPersons
                .subscribe()
                .with(
                        person -> { System.out.println("Consumindo " + person);},
                        fail -> fail.getMessage()
                );
    }*/

    /*@Incoming("my-channel")
    void consume(final Person person) {
        System.out.println("Consuming: " + person);
    }

    @Outgoing("to")
    @Incoming("from")
    Multi<Person> processStream(Multi<String> inputStream) {
        return inputStream.onItem().transform(Person::new);
    }

    @Incoming("to")
    void consumingName(final Person person) {
        System.out.println("Consumindo persons: " + person);
    }*/
}
