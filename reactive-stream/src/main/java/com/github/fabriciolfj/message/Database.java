package com.github.fabriciolfj.message;

import com.github.fabriciolfj.entity.PersonEntity;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Database {

    @NonBlocking
    @Incoming("database")
    public Uni<Void> write(final PersonEntity personEntity) {
        System.out.println("Recebido para persistencia");
        return Panache.withTransaction(personEntity::persist).invoke(() -> System.out.println("item salvo"))
                .replaceWithVoid();
    }
}
