package com.github.fabriciolfj.reactive;

import io.smallrye.mutiny.Multi;

public class StreamsExample {

    public static void main(String[] args) {
        final Multi<String> streams = Multi.createFrom().items("a", "b", "c", "d", "e", "f");

        streams.onItem()
                .transform(s -> toSquare(s))
                .onFailure()
                .recoverWithItem(e -> getFallback(e))
                .subscribe()
                .with(
                        item -> System.out.println(item),
                        err -> System.out.println(err.getMessage()),
                        () -> System.out.println("complete")
                );
    }

    private static String toSquare(final String circle) {
        if (circle.equals("d")) {
            throw new RuntimeException("teste error");
        }
        return circle;
    }

    private static String getFallback(final Throwable failure) {
        return "Hello";
    }
}
