package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.entity.Customer;
import com.github.fabriciolfj.service.OrderService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/v1/customers")
public class CustomerController {

    @Inject
    OrderService orderService;

    @GET
    public Multi<Customer> getAll() {
        return Customer.streamAll(Sort.by("name"));
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> deleteCustomer(@RestPath final Long id) {
        return Panache.withTransaction(
                () -> Customer.<Customer>deleteById(id)
                        .map(deleted -> deleted ? Response.noContent().build() : Response.status(404).build()));
    }

    @PUT
    @Path("{id}")
    public Uni<Response> updateCustomer(@RestPath final Long id, @Valid Customer customer) {
        return Panache.withTransaction(
                () -> Customer.<Customer>findById(id)
                        .onItem()
                        .ifNotNull().invoke(r -> r.name = customer.name)
                        .onItem().ifNotNull().transform(r -> Response.accepted().entity(r).build())
                        .onItem().ifNull().continueWith(Response.ok().status(404).build()));
    }

    @POST
    public Uni<Response> createCustomer(@Valid Customer customer) {
        return Panache.withTransaction(customer::persist)
                .replaceWith(Response.ok(customer).status(Response.Status.CREATED).build());
    }

    @GET
    @Path("{id}")
    public Uni<Response> findByCustomer(@RestPath Long id) {
        var customer = Customer.<Customer>findById(id)
                .onItem()
                .ifNull()
                .failWith(new WebApplicationException("Customer not found: " + id, Response.Status.NOT_FOUND));

        var orders = orderService.getOrdersForCustomer(id);

        return Uni.combine()
                .all()
                .unis(customer, orders)
                .combinedWith((c, o) -> {
                    c.orders = o;
                    return c;
                }).onItem()
                .transform(r -> Response.accepted().entity(r).build());
    }
}
