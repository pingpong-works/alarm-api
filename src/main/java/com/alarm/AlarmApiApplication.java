package com.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AlarmApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(AlarmApiApplication.class, args);
  }
}
