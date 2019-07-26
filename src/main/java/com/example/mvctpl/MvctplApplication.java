package com.example.mvctpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MvctplApplication {

  public static void main(String[] args) {
    SpringApplication.run(MvctplApplication.class, args);
  }

}
