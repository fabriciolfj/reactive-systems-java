package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Random;

public record User(Integer id, String name) {

    private static Random random = new Random();

    private static List<User> users = List.of(new User(1, "Fabricio"),
            new User(2, "Jacob"),
            new User(3, "Felicio"),
            new User(4, "Lucas"));

    public static Uni<User> userFabricio() {
        return Uni.createFrom().item(new User(1, "Fabricio"));
    }

    public static User randomUser() {
        return users.get(random.nextInt(0, 3));
    }

    public static Multi<User> users() {
        return Multi.createFrom()
                .items(new User(1, "Fabricio"),
                        new User(2, "Jacob"),
                        new User(3, "Felicio"),
                        new User(4, "Lucas"),
                        new User(5, "Fabricio"));
    }

    public String testFail() {
        throw new RuntimeException("Usuario com falha");
    }

}
