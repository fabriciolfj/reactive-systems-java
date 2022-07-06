package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.User;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectItensTest {

    public static void main(String[] args) {
        User.users()
                .collect()
                .asList()
                .subscribe()
                .with(s -> s.stream().forEach(System.out::println));

        User.users()
                .collect()
                .with(Collectors.counting())
                .subscribe()
                .with(System.out::println);

        User.users()
                .collect()
                .asMap(item -> item.id())
                .subscribe()
                .with(System.out::println);
    }
}
