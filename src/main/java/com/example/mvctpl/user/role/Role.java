package com.example.mvctpl.user.role;

import com.example.mvctpl.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
public class Role extends AbstractEntity {
  private String name;
}
