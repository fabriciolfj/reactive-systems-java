package com.github.fabriciolfj;

import io.smallrye.mutiny.Multi;

public record Order(Long id, String user) {

    public static Multi<Order> getOrdersUser(final User user) {
        return getOrders()
                .filter(s -> s.user.equals(user.name()));
    }


    public static Multi<Order> getOrders() {
        return Multi.createFrom()
                .items(
                        new Order(1L, "Jacob"),
                        new Order(2L, "Fabricio"),
                        new Order(3L, "Fabricio"),
                        new Order(4L, "Fabricio"),
                        new Order(5L, "Fabricio"),
                        new Order(6L, "Lucas"),
                        new Order(7L, "Jacob"),
                        new Order(8L, "Felicio"),
                        new Order(9L, "Jacob"),
                        new Order(10L, "Jacob"),
                        new Order(11L, "Lucas"),
                        new Order(12L, "Lucas")
                );
    }
}
