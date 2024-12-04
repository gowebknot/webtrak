package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.UserCreateDTO;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateService class.
 * It will have business logic.
 */
@Service
@Slf4j
public class UserService {

  final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(UserCreateDTO userDTO) {
    User existUser = this.userRepository.checkIfUserExists(userDTO.getEmpId(), userDTO.getEmail());

    if (existUser != null) {
      log.error("User with email or empId already exists");
      return null;
    }

    User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setEmpId(userDTO.getEmpId());
            user.setName(userDTO.getName());

    return this.userRepository.save(user);
  }

  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public User getUserByEmpId(String empId) {
    return this.userRepository.findByEmpId(empId);
  }

}
