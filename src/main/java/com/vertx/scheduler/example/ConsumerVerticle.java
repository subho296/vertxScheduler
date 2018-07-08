package com.vertx.scheduler.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;

public class ConsumerVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        //Launcher.main(new String[]{"run", ConsumerVerticle.class.getName(), "--ha", "--cluster", "--cluster-host", "127.0.0.1"});
        Launcher.main(new String[]{"run", ConsumerVerticle.class.getName(), "-ha"});
    }
    public void start(Future<Void> startFuture) {
        HttpClient httpClient = vertx.createHttpClient();

        vertx.eventBus().consumer("scheduleEvents", message -> {
            httpClient.getNow(8080, "localhost", "/api/trigger", new Handler<HttpClientResponse>() {

                @Override
                public void handle(HttpClientResponse httpClientResponse) {
                    System.out.println("Confirmation for trigger");
                }
            });
            long scheduleTime = (long) message.body();
            long current = System.currentTimeMillis();
            System.out.println("Delay in trigger = "
                    + (current - scheduleTime));
        });
    }
}
