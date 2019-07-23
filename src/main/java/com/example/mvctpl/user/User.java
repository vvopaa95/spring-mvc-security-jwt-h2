package com.example.mvctpl.user;

import com.example.mvctpl.core.AuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
class User extends AuditEntity {
  private String userName;
  private String password;


  //private List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}
