package com.webknot.webtrak.dto;

import com.webknot.webtrak.entity.User;

public class UserOutputDTO {
    private Long id;
    private String empId;
    private String email;
    private String name;
    private String privateKey;

    public UserOutputDTO(User user) {
        id = user.getId();
        empId = user.getEmpId();
        email = user.getEmail();
        name = user.getName();
        privateKey = user.getPrivateKey();
    }
}
