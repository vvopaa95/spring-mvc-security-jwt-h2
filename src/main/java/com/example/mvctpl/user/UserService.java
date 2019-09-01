package com.example.mvctpl.user;

import com.example.mvctpl.user.pojo.CommonUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Slf4j
@Service("userServiceMain")
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(final String username) {
    try {
      User users = getByUsername(username);
      return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), new ArrayList<>());
    } catch (Exception e) {
      log.info(e.getMessage());
      throw new UsernameNotFoundException("User " + username + " was not found in the database");
    }
  }

  User getByUsername(final String name) {
    return userRepository.findByUsername(name);
  }

  Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  User createUser(String username, String password) {
    return userRepository.save(new User(username, password));
  }

  CommonUser getCommonUserInfo(long id) {
    return userRepository.getById(id);
  }

  long countUsersByRoleName(String roleName) {
    return userRepository.countByRole_Name(roleName);
  }
}
