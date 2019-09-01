package com.example.mvctpl.user;

import com.example.mvctpl.core.AuditEntity;
import com.example.mvctpl.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends AuditEntity {
  @Column(unique=true)
  private String username;
  private String password;

  @ManyToMany
  @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> role;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
