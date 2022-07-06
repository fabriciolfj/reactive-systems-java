package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.User;
import io.smallrye.mutiny.Uni;

public class SelectWhereTest {

    public static void main(String[] args) {
        User.users()
                .select()
                //.when(u -> Uni.createFrom().item(u) assincrono
                //        .onItem().transform(s -> s.name().equals("Jacob")))
                //.where(c -> c.name().equals("Jacob")) sincrono
                .distinct()
                .onItem()
                .transform(s -> s.name().toUpperCase())
                .subscribe()
                .with(System.out::println);
    }
}
