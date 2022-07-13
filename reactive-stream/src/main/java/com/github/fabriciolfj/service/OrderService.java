package com.github.fabriciolfj.service;

import com.github.fabriciolfj.entity.Order;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    PgPool pgPool;

    public Uni<List<Order>> getOrdersForCustomer(final Long customer) {
        return pgPool
                .preparedQuery("Select id, customerid, description, total From orders where customerid = $1")
                .execute(Tuple.of(customer))
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(s -> Order.from(s))
                .collect().asList();
    }
}
