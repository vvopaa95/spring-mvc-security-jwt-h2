package com.example.mvctpl.user;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);
}
