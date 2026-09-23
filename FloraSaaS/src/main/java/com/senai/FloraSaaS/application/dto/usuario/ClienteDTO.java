package com.senai.FloraSaaS.application.dto.usuario;

import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.enums.usuario.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

public class ClienteDTO {
    public record ClienteRequest(
            @NotBlank(message = "O nome do Cliente é obrigatório.")
            @Schema(description = "Nome do Cliente", example = "Fulano")
            String nomeCliente,

            @NotBlank(message = "O nomePlanta do Cliente é obrigatório.")
            @Schema(description = "Nome do Cliente", example = "Fulano de Tal")
            LocalDate dataNascimentoCliente,

            @NotBlank(message = "O e-mail do Cliente é obrigatório.")
            @Schema(description = "E-mail do Cliente", example = "clienteflora@flora.com")
            String emailCliente,

            @NotBlank(message = "A senha é obrigatória.")
            @Size(min = 8,message = "A senha deve ter no mínimo 8 caracteres." )
            @Schema(description = "Senha do Cliente", example = "senhaSegura123")
            String senhaCliente
    ) {
        public Cliente toEntity() {
            return Cliente.builder()
                    .nome(this.nomeCliente)
                    .email(this.emailCliente)
                    .senha(this.senhaCliente)
                    .dataNascimento(this.dataNascimentoCliente)
                    .role(Role.CLIENTE)
                    .build();
        }
    }

    @Builder
    public record ClienteResponse(
            @Schema(description = "ID do Cliente", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
            String idCliente,

            @Schema(description = "Nome do Cliente", example = "Fulano")
            String nomeCliente,

            @Schema(description = "Idade do Cliente", example = "25")
            LocalDate dataNascimentoCliente,

            @Schema(description = "E-mail do Cliente", example = "clienteflora@flora.com")
            String emailCliente
    ) {
        public static ClienteResponse fromEntity(Cliente cliente) {
            return ClienteResponse.builder()
                    .idCliente(cliente.getIdUsuario())
                    .nomeCliente(cliente.getNome())
                    .emailCliente(cliente.getEmail())
                    .dataNascimentoCliente(cliente.getDataNascimento())
                    .build();
        }
    }

}