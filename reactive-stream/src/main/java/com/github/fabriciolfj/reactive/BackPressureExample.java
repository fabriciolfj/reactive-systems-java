package com.github.fabriciolfj.reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.time.Duration;

public class BackPressureExample {

    public static void main(String[] args) throws InterruptedException {
        Multi.createFrom().ticks().every(Duration.ofMillis(10))
                //.onOverflow().buffer(250)
                //.onOverflow().invoke(x -> System.out.println("Drop item: " + x)).drop()
                .emitOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(BackPressureExample::canOnlyConsumeOneItemPerSecond)
                .subscribe().with(
                        item -> System.out.printf("Item: %s\n", item),
                        error -> System.out.printf("Error : %s\n", error)
                );

        Thread.sleep(1000);
    }

    private static long canOnlyConsumeOneItemPerSecond(long x) {
        try {
            Thread.sleep(1000);
            return x;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return x;
        }
    }
}
