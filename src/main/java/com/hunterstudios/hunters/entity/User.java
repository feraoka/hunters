package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String account;
    private String password;
    private String email;
    private String name;
}
