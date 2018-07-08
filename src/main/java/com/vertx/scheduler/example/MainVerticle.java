package com.vertx.scheduler.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MainVerticle extends AbstractVerticle{

    public void start(Future<Void> startFuture) {
        vertx.deployVerticle(new RestAPIVerticle());
        vertx.deployVerticle(new ConsumerVerticle());
    }
}
