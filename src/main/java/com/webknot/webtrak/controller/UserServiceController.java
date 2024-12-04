package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.UserCreateDTO;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateServiceController class.
 * It will have all API end points definition.
 */
@RestController
public class UserServiceController extends BaseController {

    private final UserService userService;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("user")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User user = userService.createUser(userCreateDTO);

        if (user != null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "user")
    public User getUser(@RequestParam(required = false) String email,
                        @RequestParam(required = false) String empId) {
        if (email != null) {
            return userService.getUserByEmail(email);
        } else if (empId != null) {
            return userService.getUserByEmpId(empId);
        } else {
            throw new BadRequestException("Invalid query parameter");
        }
    }

}
