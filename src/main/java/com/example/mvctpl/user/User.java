package com.example.mvctpl.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User {

  @Id
  @GeneratedValue
  private Long id;
  private String userName;
  private String password;


  //private List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}
