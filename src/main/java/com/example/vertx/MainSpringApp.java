package com.example.vertx;

import io.vertx.core.Vertx;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainSpringApp {

  @Autowired
  private MainVerticle mainVerticle;
  @Autowired
  private LineVerticle lineVerticle;

  public static void main(String[] args) {
    SpringApplication.run(MainSpringApp.class, args);
  }

  @PostConstruct
  public void init() {
    Vertx vertxApp = Vertx.vertx();
    vertxApp.deployVerticle(mainVerticle);
    vertxApp.deployVerticle(lineVerticle);
  }
}
