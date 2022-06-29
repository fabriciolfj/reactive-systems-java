package com.github.fabriciolfj.reactive;

import static com.github.fabriciolfj.UserService.getAllUsers;

public class MultiApi {

    public static void main(String[] args) {
        getAllUsers()
                .onItem()
                .transform(user -> user.name().toLowerCase())
                .select().where(name -> name.startsWith("s"))
                .collect().asList()
                .subscribe().with(System.out::println);
    }
}
