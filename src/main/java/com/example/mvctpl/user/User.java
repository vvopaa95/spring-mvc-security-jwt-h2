package com.example.mvctpl.user;

import com.example.mvctpl.core.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@Entity
public class User extends AuditEntity {
  @Column(unique=true)
  private String username;
  private String password;
}
