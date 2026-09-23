package com.senai.FloraSaaS.interface_ui.controller.usuario;

import com.senai.FloraSaaS.application.dto.usuario.UsuarioDTO;
import com.senai.FloraSaaS.application.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.parameters.ValidatedParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário com os dados fornecidos.",
            requestBody = @RequestBody(
                    description = "Dados do usuário a ser criado",
                    required = true,
                    content = @Content(
                           schema = @Schema(implementation = UsuarioDTO.UsuarioRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                       "nome": "Fulano de Tal",
                                       "email": "fulanodetal@email.com",
                                       "senha": "senhaSegura123",
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO.UsuarioResponseDTO> criarUsuario(@Valid @org.springframework.web.bind.annotation.RequestBody UsuarioDTO.UsuarioRequestDTO request) {
        UsuarioDTO.UsuarioResponseDTO usuario = service.criarUsuario(request);

        return ResponseEntity.created(URI.create("/api/usuario/" + usuario.id()))
                .body(usuario);
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO.UsuarioResponseDTO>>  listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os detalhes de um usuário específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO.UsuarioResponseDTO>  buscarUsuarioPorId(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(service.buscarUsuarioPorId(id));
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO.UsuarioResponseDTO> atualizarUsuario(
            @PathVariable String id,
            @org.springframework.web.bind.annotation.RequestBody UsuarioDTO.UsuarioRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarUsuario(id, dto));
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletarUsuario(@PathVariable String id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}