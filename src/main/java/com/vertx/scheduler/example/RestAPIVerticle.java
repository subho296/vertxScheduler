package com.vertx.scheduler.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a verticle. A verticle is a _Vert.x component_. This verticle is implemented in Java, but you can
 * implement them in JavaScript, Groovy or even Ruby.
 */
public class RestAPIVerticle extends AbstractVerticle {

    private Map<Integer, String> products = new LinkedHashMap<>();

    private RandomStringUtils randomStringUtils = new RandomStringUtils();


    private AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();
    public static void main(String[] args) {
        Launcher.main(new String[]{"run", RestAPIVerticle.class.getName(), "--ha"});
        //Launcher.main(new String[]{"bare"});
    }
  /**
   * This method is called when the verticle is deployed. It creates a HTTP server and registers a simple request
   * handler.
   * <p/>
   * Notice the `listen` method. It passes a lambda checking the port binding result. When the HTTP server has been
   * bound on the port, it call the `complete` method to inform that the starting has completed. Else it reports the
   * error.
   *
   * @param fut the future
   */
  @Override
  public void start(Future<Void> fut) {

    createSomeData();

    // Create a router object.
    Router router = Router.router(vertx);

    // Bind "/" to our hello message.
    router.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response
          .putHeader("content-type", "text/html")
          .end("<h1>Hello from my first Vert.x 3 application</h1>");
    });

    router.route("/assets/*").handler(StaticHandler.create("assets"));

      router.get("/api/whiskies").handler(this::getAll);
      router.route("/api/whiskies*").handler(BodyHandler.create());
      router.route("/api/addschedule*").handler(BodyHandler.create());
      router.post("/api/addschedule").handler(this::addSchedule);
      router.get("/api/trigger").handler(this::trigger);
      router.get("/api/count").handler(this::count);


    // Create the HTTP server and pass the "accept" method to the request handler.
    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
            // Retrieve the port from the configuration,
            // default to 8080.
            config().getInteger("http.port", 8080),
            result -> {
              if (result.succeeded()) {
                fut.complete();
              } else {
                fut.fail(result.cause());
              }
            }
        );
  }

    private void count(RoutingContext routingContext) {
        routingContext.response().setStatusCode(200).
                end(Json.encodePrettily(count.get()));
    }

    private void trigger(RoutingContext routingContext) {
        System.out.println("Trigger received");
        routingContext.response().setStatusCode(200).end();
        count.getAndDecrement();
    }

    private void addSchedule(RoutingContext routingContext) {
        count.getAndIncrement();
        long current = System.currentTimeMillis();
        int delay = (60) * 1000;
        //int delay = 5000;

        vertx.setTimer(delay, id -> {
            vertx.eventBus().publish("scheduleEvents", current + delay);
            System.out.println("Published the event");
        });
        routingContext.response().setStatusCode(200).end();
  }




  private void getAll(RoutingContext routingContext) {
    // Write the HTTP response
    // The response is in JSON using the utf-8 encoding
    // We returns the list of bottles
    routingContext.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(products.values()));
  }

  private void createSomeData() {
  }

}
