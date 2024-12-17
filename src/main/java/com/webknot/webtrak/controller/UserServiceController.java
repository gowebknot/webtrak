package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.GenericResponseDTO;
import com.webknot.webtrak.dto.UserCreateDTO;
import com.webknot.webtrak.dto.UserOutputDTO;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.UserService;
import com.webknot.webtrak.util.Utils;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${webtrak.adminPassword}")
    private String adminPassword;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("user")
    public ResponseEntity<GenericResponseDTO> createUser(@RequestHeader("Authorization") String authorization,
                                                         @RequestBody UserCreateDTO userCreateDTO) {

        Utils.verifyPassword(adminPassword, authorization);

        User user = userService.createUser(userCreateDTO);
        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Unable to create user", null));
        }
        return ResponseEntity.ok().body(new GenericResponseDTO("success", user));
    }

    @GetMapping("privateKey/{userEmail}")
    public ResponseEntity<GenericResponseDTO> getPrivateKey(@RequestHeader("Authorization") String authorization,
            @PathVariable(value = "userEmail") String userEmail) {

        Utils.verifyPassword(adminPassword, authorization);

        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().body(new GenericResponseDTO("success", user.getPrivateKey()));

    }

    @GetMapping(value = "user")
    public ResponseEntity<GenericResponseDTO> getUser(@RequestParam(required = false) String email,
                        @RequestParam(required = false) String empId) {
        if (email != null) {
            return ResponseEntity.ok().body(new GenericResponseDTO("success", userService.getUserByEmail(email)));
        } else if (empId != null) {
            return ResponseEntity.ok().body(new GenericResponseDTO("success", userService.getUserByEmpId(empId)));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
