package com.senai.FloraSaaS.application.service.usuario;

import com.senai.FloraSaaS.application.dto.usuario.UsuarioDTO;
import com.senai.FloraSaaS.domain.exception.usuario.UsuarioNaoEncontradoException;
import com.senai.FloraSaaS.domain.entity.usuario.Usuario;
import com.senai.FloraSaaS.domain.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDTO.UsuarioResponseDTO criarUsuario(UsuarioDTO.UsuarioRequestDTO dto) {
        Usuario usuarioNovo = dto.toEntity();

        usuarioNovo.setSenha(passwordEncoder.encode(dto.senha()));
        return UsuarioDTO.UsuarioResponseDTO.fromEntity(
                repository.save(usuarioNovo)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDTO.UsuarioResponseDTO buscarUsuarioPorId(String id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return UsuarioDTO.UsuarioResponseDTO.fromEntity(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UsuarioDTO.UsuarioResponseDTO> listarUsuarios() {
        return repository.findAll().stream()
                .map(UsuarioDTO.UsuarioResponseDTO::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public UsuarioDTO.UsuarioResponseDTO atualizarUsuario(String idUsuario, UsuarioDTO.UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }

        return UsuarioDTO.UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletarUsuario(String id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        repository.delete(usuario);
    }

}