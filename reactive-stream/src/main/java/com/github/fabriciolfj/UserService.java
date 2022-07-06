package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;

public class UserService {

    private UserService() {

    }

    public static Multi<User> getAllUsers() {
        return Multi.createFrom().items(
                new User(1, "Fabricio"),
                new User(2, "Lucas"),
                new User(3, "Suzana"),
                new User(4, "Souza"));
    }
}
