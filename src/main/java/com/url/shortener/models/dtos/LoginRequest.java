package com.url.shortener.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
