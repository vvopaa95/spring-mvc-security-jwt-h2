package com.example.mvctpl.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("custom")
  public String custom() {
    //User user = userService.loadByUsername("root");
    return passwordEncoder.encode("dima");
  }
}
