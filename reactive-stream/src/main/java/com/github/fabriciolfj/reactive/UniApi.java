package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.User;
import com.github.fabriciolfj.UserService;

public class UniApi {

    public static void main(String[] args) {
        UserService
                .getAllUsers()
                .select().where(user -> user.name().equals("Fabricio"))
                .toUni()
                .onFailure().recoverWithItem(new User(1, "Error"))
                .subscribe()
                .with(s -> System.out.println(s.name()),
                        err ->  System.out.println(err)
                        //() -> System.out.println("Complete")
                );
    }
}
