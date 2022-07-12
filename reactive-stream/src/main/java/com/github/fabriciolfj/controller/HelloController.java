package com.github.fabriciolfj.controller;

import io.smallrye.common.annotation.NonBlocking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/hello")
public class HelloController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    //@NonBlocking
    public String hello() {
        return "Hello resteasy Reactive from  " + Thread.currentThread().getName();
    }

    @GET
    @Path("/blocking")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloBlocking() {
        return "Hello resteasy Reactive from  " + Thread.currentThread().getName();
    }
}
