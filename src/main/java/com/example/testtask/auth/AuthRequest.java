package com.example.testtask.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
