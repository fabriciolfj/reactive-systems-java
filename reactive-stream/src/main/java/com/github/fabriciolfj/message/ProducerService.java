package com.github.fabriciolfj.message;

import com.github.fabriciolfj.Person;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProducerService {

    @Channel("from")
    MutinyEmitter<String> producer;

    public Uni<Void> createMessage(final String name) {
        return producer.send(name)
                .invoke(r -> System.out.println("Valor produzido: " + r));
    }

    //@Outgoing("my-channel")
    Multi<Person> produceAStreamOfPersons() {
        return Multi.createFrom()
                .items(new Person("Fabricio"), new Person("Suzana"))
                .invoke(r -> System.out.println("Item enviado: " + r));
    }
}
