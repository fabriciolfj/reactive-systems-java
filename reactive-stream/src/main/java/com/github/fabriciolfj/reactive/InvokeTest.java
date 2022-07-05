package com.github.fabriciolfj.reactive;

import io.smallrye.mutiny.Multi;

public class InvokeTest {

    public static void main(String[] args) {
        Multi.createFrom()
                .items("Fabricio", "Jacob")
                .onSubscription().invoke(s -> System.out.println("Inscrito"))
                .onCancellation().invoke(() -> System.out.println("Evento cancelado"))
                .onItem().invoke(item -> System.out.println("Item emitido"))
                .onFailure().invoke(e -> System.out.println("Ocorreu falha"))
                .onCompletion().invoke(() -> System.out.println("Eventos concluídos"))
                .subscribe()
                .with(System.out::println);
    }
}
