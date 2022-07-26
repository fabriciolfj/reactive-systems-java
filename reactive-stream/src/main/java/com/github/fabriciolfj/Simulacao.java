package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;

import java.time.Duration;
import java.util.List;

public class Simulacao {

    public static void main(String[] args) {
        var simulacao = new Simulacao();
        simulacao.processGroup(simulacao.group(simulacao.ticks()))
                .subscribe()
                .with(System.out::println);
    }

    public Multi<Long> ticks() {
        return Multi.createFrom().ticks()
                .every(Duration.ofSeconds(1))
                .onOverflow().drop();
    }


    public Multi<List<String>> group(final Multi<Long> stream) {
        return stream.onItem().transform(l -> Long.toString(l))
                .group().intoLists().of(5);
    }

    public Multi<String> processGroup(final Multi<List<String>> list) {
        return list.onItem().transform(c -> "Hello " + String.join(",", c));
    }
}
