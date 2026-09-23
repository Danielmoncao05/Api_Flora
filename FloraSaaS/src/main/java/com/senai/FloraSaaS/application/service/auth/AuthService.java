package com.senai.FloraSaaS.application.service.auth;

import com.senai.FloraSaaS.application.dto.auth.AuthDTO;
import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.entity.usuario.Usuario;
import com.senai.FloraSaaS.domain.enums.usuario.Role;
import com.senai.FloraSaaS.domain.exception.usuario.UsuarioNaoEncontradoException;
import com.senai.FloraSaaS.domain.repository.usuario.UsuarioRepository;
import com.senai.FloraSaaS.infrastructure.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public Map<String, String> login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String accessToken = jwt.generateAccessToken(usuario.getEmail(), usuario.getRole().name());
        String refreshToken = jwt.generateRefreshToken(usuario.getEmail());

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public Map<String, String> refresh(String refreshToken) {
        if (!jwt.isValid(refreshToken)) {
            throw new BadCredentialsException("Refresh token inválido ou expirado");
        }

        String email = jwt.extractEmail(refreshToken);
        Usuario usuario = usuarios.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        String newAccess = jwt.generateAccessToken(usuario.getEmail(), usuario.getRole().name());
        return Map.of("accessToken", newAccess);
    }
    public Map<String, String> signup(AuthDTO.SignUpRequest req) {
        Cliente cliente = new Cliente();

        cliente.setNome(req.nome());
        cliente.setEmail(req.email());
        cliente.setDataNascimento(req.dataNascimentoCliente());
        cliente.setSenha(encoder.encode(req.senha()));
        cliente.setRole(Role.CLIENTE);
        cliente.setAtivo(true);

        usuarios.save(cliente);

        String accessToken = jwt.generateAccessToken(cliente.getEmail(), cliente.getRole().name());
        String refreshToken = jwt.generateRefreshToken(cliente.getEmail());

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

}