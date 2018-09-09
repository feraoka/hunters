package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class UserRole {
    private String account;
    private String password;
    private String[] roles;
}
