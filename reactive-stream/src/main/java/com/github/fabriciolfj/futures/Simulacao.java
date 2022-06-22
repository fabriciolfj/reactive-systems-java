package com.github.fabriciolfj.futures;

import io.smallrye.mutiny.tuples.Tuple2;

import java.util.concurrent.CompletableFuture;

public class Simulacao {

    public static void main(String[] args) {
        final GreetingService service = new GreetingService();

        service.greeting("Fabricio")
                .thenApply(String::toUpperCase)
                .thenAccept(System.out::println);

        System.out.println("=======================");
        service.greeting("Fabricio")
                .thenCompose(gfab -> service.greeting("Lucas")
                                        .thenApply(glu -> Tuple2.of(gfab, glu)))
                .thenAccept(tuple -> System.out.println(tuple.getItem1() + " " + tuple.getItem2()));

        System.out.println("==========================composicao paralela====================");
        var compl1 = service.greeting("fabricio");
        var compl2 = service.greeting("lucas");

        CompletableFuture.allOf(compl1, compl2)
                .thenAccept(igonore -> System.out.println(compl1.join() + " " + compl2.join()));

        System.out.println("=====================");
        service.greeting("fabricio")
                .exceptionally(e -> "hello");

    }

    static class GreetingService {

        CompletableFuture<String> greeting(final String name) {
            return CompletableFuture.completedFuture("Hello " + name);
        }
    }
}
