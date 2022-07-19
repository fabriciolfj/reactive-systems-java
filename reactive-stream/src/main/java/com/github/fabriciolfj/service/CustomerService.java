package com.github.fabriciolfj.service;

import com.github.fabriciolfj.entity.Customer;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@ApplicationScoped
public class CustomerService {

    private static final String CUSTOMER_HASH_PREFIX = "cust:";

    @Inject
    private ReactiveRedisClient reactiveRedisClient;

    public Multi<Customer> allCustomers() {
        return reactiveRedisClient.keys("*")
                .onItem()
                .transformToMulti(response -> Multi.createFrom()
                        .iterable(response)
                        .map(Response::toString))
               .onItem()
                .transformToUniAndMerge(key -> reactiveRedisClient.hgetall(key)
                                                .map(resp ->
                                                        constructCustomer(Long.parseLong(key.substring(CUSTOMER_HASH_PREFIX.length())), resp)));
    }

    public Uni<Customer> getCustomer(Long id) {
        return reactiveRedisClient.hgetall(CUSTOMER_HASH_PREFIX + id)
                .map(resp -> resp.size() > 0 ?
                        constructCustomer(id, resp)
                        : null);
    }

    public Uni<Customer> storeCustomer(final Customer customer) {
        return reactiveRedisClient.hmset(Arrays.asList(CUSTOMER_HASH_PREFIX + customer.id, "name", customer.name))
                .onItem()
                .transform(resp -> {
                    if (resp.toString().equals("OK")) {
                        return customer;
                    } else {
                        throw new NoSuchElementException();
                    }
                });
    }

    public Uni<Customer> updateCustomer(final Customer customer) {
        return getCustomer(customer.id)
                .onItem()
                .transformToUni((cust) -> {
                    if (cust ==null) {
                        return Uni.createFrom().failure(new NotFoundException());
                    }

                    cust.name = customer.name;
                    return storeCustomer(customer);
                });
    }

    public Uni<Void> deleteCustomer(final Long id) {
        return reactiveRedisClient.hdel(Arrays.asList(CUSTOMER_HASH_PREFIX + id, "name"))
                .map(response -> response.toInteger() == 1? true : null)
                .onItem().ifNull().failWith(new NotFoundException())
                .onItem().ifNotNull().transformToUni(r -> Uni.createFrom().nullItem());
    }

    Customer constructCustomer(long id, Response response) {
        Customer customer = new Customer();
        customer.id = id;
        customer.name = response.get("name").toString();
        return customer;
    }
}
