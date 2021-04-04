package com.eshop.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *
 * The {@link EnableConfigServer} annotation defines that this application will
 * serve as the REST based API for providing external configuration.
 *
 * The external repository from where the configuration will be picked up is
 * defined in the {@linkplain application.yml} file.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConfigApplication.class, args);
  }
}
