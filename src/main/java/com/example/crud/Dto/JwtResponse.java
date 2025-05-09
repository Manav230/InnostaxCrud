package com.example.crud.Dto;

public class JwtResponse {
    private String token;
    private long expiresAt;

    public JwtResponse(String token, long expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
