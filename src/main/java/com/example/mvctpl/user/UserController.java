package com.example.mvctpl.user;

import com.example.mvctpl.user.payload.CreateUserRequest;
import com.example.mvctpl.user.pojo.CommonUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("user")
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("encode")
  public String custom(@RequestParam String password) {
    return passwordEncoder.encode(password);
  }

  @GetMapping("all")
  public Iterable<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @PostMapping("create")
  public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
    return Optional
      .ofNullable(
        userService.createUser(createUserRequest.getUsername(), passwordEncoder.encode(createUserRequest.getPassword())))
      .map(user -> new ResponseEntity<>(HttpStatus.CREATED))
      .orElse(ResponseEntity.badRequest().body(new Object()));
  }

  @GetMapping("/get/{username}")
  public ResponseEntity getUser(@PathVariable String username) {
    return Optional.ofNullable(userService.getByUsername(username))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.badRequest().body(null));
  }

  @GetMapping("count/by-role")
  public long countUsersByRole(@RequestParam String roleName) {
    return userService.countUsersByRoleName(roleName);
  }

  @GetMapping("common/{id}")
  public CommonUser getCommonUserInfo(@PathVariable long id) {
    return userService.getCommonUserInfo(id);
  }
}
