package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.Product;
import com.github.fabriciolfj.User;
import io.smallrye.mutiny.Multi;

import java.time.Duration;

public class CombineMultiTest {

    public static void main(String[] args) {
        Multi.createBy()
                .combining()
                .streams(getUsers(), getRecommendations())
                .asTuple()
                .onItem()
                .transform(t -> "Hello " + t.getItem1().name() + ", we recommend you " + t.getItem2().name())
                .subscribe()
                .with(System.out::println);
    }

    private static Multi<User> getUsers() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transform(x -> User.randomUser());
    }

    private static Multi<Product> getRecommendations() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transform(x -> Product.getRecommendationProduct(x));
    }
}
