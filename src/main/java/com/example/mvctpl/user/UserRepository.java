package com.example.mvctpl.user;

import com.example.mvctpl.user.pojo.CommonUser;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);

  long countByRole_Name(String name);

  CommonUser getById(long id);
}
