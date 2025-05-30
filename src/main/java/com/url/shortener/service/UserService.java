package com.url.shortener.service;

import com.url.shortener.models.User;
import com.url.shortener.models.dtos.LoginRequest;
import com.url.shortener.security.jwt.JwtAuthenticationResponse;

public interface UserService {
    User registerUser(User user);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    User findByUsername(String name);
}
