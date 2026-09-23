package com.senai.FloraSaaS.application.dto.usuario;

import com.senai.FloraSaaS.domain.entity.usuario.Usuario;
import com.senai.FloraSaaS.domain.enums.usuario.Role;
import lombok.Builder;

public class UsuarioDTO {
    public record UsuarioRequestDTO(
            String nome,
            String email,
            String senha
    ) {
        public Usuario toEntity() {
            return Usuario.builder()
                    .nome(this.nome)
                    .email(this.email)
                    .senha(this.senha)
                    .ativo(true)
                    .role(Role.USUARIO)
                    .build();
        }
    }

    @Builder
    public record UsuarioResponseDTO(
            String id,
            String nome,
            String email
    ) {
        public static UsuarioResponseDTO fromEntity(Usuario usuario) {
            return UsuarioResponseDTO.builder()
                    .id(usuario.getIdUsuario())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .build();
        }
    }

}