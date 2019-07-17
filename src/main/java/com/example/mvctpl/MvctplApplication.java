package com.example.mvctpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class MvctplApplication {

  public static void main(String[] args) {
    SpringApplication.run(MvctplApplication.class, args);
  }

}
