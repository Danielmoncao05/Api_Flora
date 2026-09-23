package com.senai.FloraSaaS.application.dto.auth;

import java.time.LocalDate;

public class AuthDTO {
    public record LoginRequest(String email, String senha) {}

    public record SignUpRequest(String nome, LocalDate dataNascimentoCliente, String email, String senha) {}

    public record AuthResponse(String accessToken, String refreshToken) {}

    public record RefreshRequest(String refreshToken) {}

    public record UserResponse(String nome, String email, String role) {}
}
