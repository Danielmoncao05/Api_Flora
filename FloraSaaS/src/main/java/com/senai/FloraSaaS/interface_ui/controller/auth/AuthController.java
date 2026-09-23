package com.senai.FloraSaaS.interface_ui.controller.auth;

import com.senai.FloraSaaS.application.dto.auth.AuthDTO;
import com.senai.FloraSaaS.application.service.auth.AuthService;
import com.senai.FloraSaaS.domain.exception.usuario.UsuarioNaoEncontradoException;
import com.senai.FloraSaaS.domain.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;
    private final UsuarioRepository usuarios;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponse> login(@RequestBody AuthDTO.LoginRequest req) {
        var tokens = auth.login(req);
        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                tokens.get("accessToken"),
                tokens.get("refreshToken")
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDTO.AuthResponse> refresh(@RequestBody AuthDTO.RefreshRequest req) {
        var newToken = auth.refresh(req.refreshToken());
        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                newToken.get("accessToken"),
                req.refreshToken()
        ));
    }

    @GetMapping("/me")
    public AuthDTO.UserResponse me(Authentication auth) {
        var usuario = usuarios.findByEmail(auth.getName())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return new AuthDTO.UserResponse(usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDTO.AuthResponse> signup(@RequestBody AuthDTO.SignUpRequest req) {
        var tokens = auth.signup(req);
        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                tokens.get("accessToken"),
                tokens.get("refreshToken")
        ));
    }
}