package com.example.mvctpl.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @PostMapping("custom")
  public String custom() {
    return "custom";
  }
}
