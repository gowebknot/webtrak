package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.UserCreateDTO;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

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
            user.setPrivateKey(generatePassword());

    return this.userRepository.save(user);
  }

  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public User getUserByEmpId(String empId) {
    return this.userRepository.findByEmpId(empId);
  }

    public String generatePassword() {
      String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
      String digits = "0123456789";
      String specialCharacters = "!@#$%^&*()-_+=<>?/|~";

      String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;

      SecureRandom random = new SecureRandom();
      StringBuilder password = new StringBuilder();

      // Ensure at least one character from each group
      password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
      password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
      password.append(digits.charAt(random.nextInt(digits.length())));
      password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

      // Fill the remaining characters randomly
      for (int i = 4; i < 8; i++) {
        password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
      }

      // Shuffle the characters to ensure randomness
      char[] passwordArray = password.toString().toCharArray();
      for (int i = passwordArray.length - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);
        // Swap characters
        char temp = passwordArray[i];
        passwordArray[i] = passwordArray[j];
        passwordArray[j] = temp;
      }

      // Convert to final password string
        return new String(passwordArray);
    }

}
