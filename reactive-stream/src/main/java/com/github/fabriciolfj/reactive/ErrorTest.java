package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.Product;
import io.smallrye.mutiny.Uni;

public class ErrorTest {

    public static void main(String[] args) {
        Uni.createFrom()
                .item(new Product(1L, null))
                .onItem()
                .transform(s -> s.name().toUpperCase())
                .onFailure().invoke(e -> System.out.println("Falha " + e.getMessage()))
                .onItem()
                .transform(s -> s + " teste")
                .onFailure().invoke(e -> System.out.println("Falha 2 " + e.getMessage()))
                .subscribe()
                .with(i -> System.out.println(i)
                        //err -> System.out.println("Error : " + err)
                );
    }
}
