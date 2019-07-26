package com.example.mvctpl.user;

import com.example.mvctpl.core.AuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
public class User extends AuditEntity {
  private String username;
  private String password;

}
