package com.github.fabriciolfj.reactive;

import io.smallrye.mutiny.Multi;

public class StreamsExample {

    public static void main(String[] args) {
        final Multi<String> dados = Multi.createFrom().items("a", "b", "c", "d", "e");

        dados.onItem()
                .transform(StreamsExample::toSquare)
                .onFailure().recoverWithItem(StreamsExample::getFallback)
                .subscribe()
                .with(
                        value -> System.out.println(value),
                        err -> System.out.println(err),
                        () -> System.out.println("complete")
                );


    }

    private static String toSquare(final String circle) {
        if (circle.equals("d")) {
            throw new RuntimeException("teste error");
        }
        return circle.toUpperCase();
    }

    private static String getFallback(final Throwable failure) {
        return "Hello";
    }
}
