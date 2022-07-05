package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.Product;
import io.smallrye.mutiny.Multi;

import java.time.Duration;

public class OnOverflowTest {

    public static void main(String[] args) {
        getRecommendations()
                .subscribe()
                .with(System.out::println);
    }

    public static Multi<Product> getRecommendations() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop()
                .onItem().transform(x -> Product.getRecommendationProduct(x));
    }
}
