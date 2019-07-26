package com.example.mvctpl.user.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequest {
  @NotNull
  private String username;
  @NotNull
  private String password;
}
