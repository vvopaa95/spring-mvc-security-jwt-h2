package com.example.mvctpl.user.pojo;

import lombok.Value;

import java.util.Date;

@Value
public class CommonUser {
  long id;
  String username;
  Date createdAt;
}
