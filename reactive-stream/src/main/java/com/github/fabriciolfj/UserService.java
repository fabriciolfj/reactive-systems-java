package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;

public class UserService {

    private UserService() {

    }

    public static Multi<User> getAllUsers() {
        return Multi.createFrom().items(
                new User("Fabricio"),
                new User("Lucas"),
                new User("Suzana"),
                new User("Souza"));
    }
}
