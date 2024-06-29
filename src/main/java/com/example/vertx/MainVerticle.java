package com.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class MainVerticle extends AbstractVerticle {

  private int counter = 0;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.setPeriodic(2000, this::increment);

    Router router = Router.router(vertx);
    router.route("/").handler(this::hello);
    router.route("/index").handler(routingContext -> {
      routingContext.response().putHeader("content-type", "text/plain").end(printThreadName() + " Index page");
    });
    router.route("/counter").handler(this::incrementCounter);
    router.route("/produce/:value").handler(this::produce);
    router.route("/counter1").handler(this::incrementCounter1);
    router.route("/counter2").handler(this::incrementCounter2);

    vertx.createHttpServer().requestHandler(router).
      listen(8888).onSuccess(http -> {
        System.out.println(printThreadName());
        System.out.println("HTTP server started on port 8888");
        startPromise.complete();
      }).onFailure(err -> {
        System.out.println(err.getMessage());
        startPromise.fail(err.getCause());
      });
  }

  private void produce(RoutingContext routingContext) {
    String value = routingContext.request().getParam("value");
    System.out.println(value);
    vertx.eventBus().publish("news-feed", value);
  }

  private void incrementCounter(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end(String.valueOf(counter));
  }
  private void incrementCounter1(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end(String.valueOf(counter));
  }
  private void incrementCounter2(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end(String.valueOf(counter));
  }

  private void hello(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end("Hello World from Vert.x-Web!");
  }

  private void increment(Long id) {
    counter++;
    System.out.println("Total count: " + counter + printThreadName());
  }

  private String printThreadName() {
    System.out.println("Thread active count: " + Thread.activeCount());
    return " [Thread]: " + Thread.currentThread().getName();
  }
}
