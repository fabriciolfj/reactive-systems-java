package com.github.fabriciolfj.reactive;

import com.github.fabriciolfj.User;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public class FailureTest {

    public static void main(String[] args) {
        addUser();
    }

    private static void addUser() {
        Uni.createFrom()
                .item(new User(null))
                .onItem().invoke(e -> System.out.println("Evento emitido"))
                .onItem().transform(s -> s.testFail())
                .onFailure().invoke(e -> System.out.println("Ocorreu a falha: " + e.getMessage()))
                .onFailure().retry()
                    .withBackOff(Duration.ofSeconds(3))
                    .atMost(3)
                .onFailure().recoverWithItem(
                        err -> "Usuario não inserido: " + err
                                .getMessage())
                .subscribe()
                .with(
                        item -> System.out.println(item),
                        err -> System.out.println(err)
                );
    }
}
