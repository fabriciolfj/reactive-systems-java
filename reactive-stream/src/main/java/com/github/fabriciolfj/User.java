package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public record User(String name) {

    public static Uni<User> userFabricio() {
        return Uni.createFrom().item(new User("Fabricio"));
    }

    public static Multi<User> users() {
        return Multi.createFrom()
                .items(new User("Fabricio"),
                        new User("Jacob"),
                        new User("Felicio"),
                        new User("Lucas"));
    }

    public String testFail() {
        throw new RuntimeException("Usuario com falha");
    }

}
