package com.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class LineVerticle extends AbstractVerticle {

  private int counter = 0;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    vertx.eventBus().consumer("news-feed", message -> {
      System.out.println("Received news: " + message.body() +" - Thread active count: "+Thread.activeCount());
    });

    vertx.eventBus().consumer("news-feed", message -> {
      System.out.println("Received news 1 : " + message.body() +" - Thread active count: "+Thread.activeCount());
    });

    vertx.eventBus().consumer("news-feed", message -> {
      System.out.println("Received news 2 : " + message.body() +" - Thread active count: "+Thread.activeCount());
    });
  }

  private void incrementCounter(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end(String.valueOf(counter));
  }

  private void hello(RoutingContext routingContext) {
    System.out.println(printThreadName());
    routingContext.response().putHeader("content-type", "text/plain").end("Hello World from Vert.x-Web!");
  }

  private void increment(Long id) {
    counter++;
    System.out.println("Total count is " + counter + printThreadName());
  }

  private String printThreadName() {
    return "[Thread]: " + Thread.currentThread().getName();
  }
}
