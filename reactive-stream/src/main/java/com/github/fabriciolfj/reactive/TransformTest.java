package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.User;

import static com.github.fabriciolfj.Order.getOrdersUser;
import static com.github.fabriciolfj.User.users;

public class TransformTest {

    public static void main(String[] args) {
        //transformMultiToMulti();
        transformUniToMulti();
    }

    private static void transformUniToMulti() {
        User.userFabricio()
                .onItem()
                .transformToMulti(s -> getOrdersUser(s))
                .subscribe()
                .with(System.out::println);
    }

    private static void transformMultiToMulti() {
        users()
                .onItem()
                //.transformToMultiAndConcatenate(s -> getOrdersUser(s))
                .transformToMultiAndMerge(s -> getOrdersUser(s))
                .onFailure().invoke(e -> System.out.println("Ocorreu uma falha"))
                .subscribe()
                .with(System.out::println);
    }
}
