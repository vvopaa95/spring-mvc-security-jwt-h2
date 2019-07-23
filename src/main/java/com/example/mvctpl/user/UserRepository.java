package com.example.mvctpl.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
  User getByUsername(String username);
}
