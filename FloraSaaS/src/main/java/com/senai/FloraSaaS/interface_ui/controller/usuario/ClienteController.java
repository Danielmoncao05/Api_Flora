package com.senai.FloraSaaS.interface_ui.controller.usuario;

import com.senai.FloraSaaS.application.dto.ambiente.AmbienteDTO;
import com.senai.FloraSaaS.application.dto.usuario.ClienteDTO;
import com.senai.FloraSaaS.application.service.ambiente.AmbienteService;
import com.senai.FloraSaaS.application.service.usuario.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final AmbienteService ambienteService;

    //CRUD normal
    @Operation(
            summary = "Criar um novo Cliente",
            description = "Cria um novo Cliente com os dados fornecidos no corpo da requisição.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteDTO.ClienteRequest.class),
                            examples = @ExampleObject(value = """
                                                   {
                                                      "nome_cliente": "Empresa XYZ",
                                                      "sobrenome_cliente": "Solucões Ambientais",
                                                      "idade_cliente": 30,
                                                      "email_cliente": "empresa@flora.com",
                                                      "senha_cliente": "senhaSegura123"
                                                   }
                                                   """
                                    )
                    )
            ),
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Requisição inválida",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = {
                                                @ExampleObject(
                                                        name = "Erro de regra de negócio genérica ainda nao pensada e estruturada",
                                                        value = "valor de exemplo"
                                                ),
                                                @ExampleObject(
                                                        name = "Erro de validação de campo",
                                                        value = """
                                                       {
                                                          "timestamp": "2024-01-01T12:00:00Z",
                                                          "status": 400,
                                                          "errors": [
                                                              {
                                                                  "field": "nome_cliente",
                                                                  "message": "O nome do idCliente é obrigatório"
                                                              },
                                                              {
                                                                  "field": "email_cliente",
                                                                  "message": "O email do idCliente deve ser um email válido"
                                                              }
                                                          ]
                                                       }
                                                       """
                                                ),
                                                @ExampleObject(
                                                        name = "Email já cadastrado",
                                                        value = """
                                                       {
                                                          "timestamp": "2024-01-01T12:00:00Z",
                                                          "status": 400,
                                                          "error": "Email já cadastrado"
                                                       }
                                                       """
                                                )
                                            }
                                    )
                            ),
                            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                            @ApiResponse(responseCode = "401", description = "Não autorizado"),
                            @ApiResponse(responseCode = "403", description = "Proibido")
                    }
    )
    @PostMapping
    public ResponseEntity<ClienteDTO.ClienteResponse> criarCliente(@Valid @org.springframework.web.bind.annotation.RequestBody ClienteDTO.ClienteRequest request){
        ClienteDTO.ClienteResponse response = clienteService.criarCliente(request);

        return ResponseEntity.created(URI.create("/api/cliente/" + response.idCliente()))
                .body(response);
    }

    @Operation(
            summary = "Listar todos os Clientes",
            description = "Retorna uma lista de todos os Clientes cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Clientes retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteDTO.ClienteResponse>> listarClientes(){
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @Operation(
            summary = "Buscar Cliente por ID",
            description = "Retorna os detalhes de um Cliente específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO.ClienteResponse> buscarPorId(@PathVariable String idCliente){
        return ResponseEntity.ok(clienteService.buscarPorId(idCliente));
    }

    @Operation(
            summary = "Atualizar Cliente",
            description = "Atualiza os dados de um Cliente existente com base no ID fornecido.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Dados do idCliente para atualização",
                    content = @Content(schema = @Schema(implementation = ClienteDTO.ClienteRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO.ClienteResponse>atualizarCliente(@PathVariable String idCliente, @org.springframework.web.bind.annotation.RequestBody ClienteDTO.ClienteRequest request){
        return ResponseEntity.ok(clienteService.atualizarCliente(idCliente, request));
    }

    @Operation(
            summary = "Excluir Cliente",
            description = "Exclui um Cliente existente com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> excluirCliente(@PathVariable String idCliente){
        clienteService.excluirCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    //-----------------------------------------------------------------------

    // Algumas funções relacionadas a ativar/inativar
    @Operation(
            summary = "Listar Clientes Ativos",
            description = "Retorna uma lista de todos os Clientes que estão ativos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Clientes ativos retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteDTO.ClienteResponse>> listarClientesAtivos(){
        return ResponseEntity.ok(clienteService.listarClientesAtivos());
    }

    @Operation(
            summary = "Listar Clientes Inativos",
            description = "Retorna uma lista de todos os Clientes que estão inativos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Clientes inativos retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/inativos")
    public ResponseEntity<List<ClienteDTO.ClienteResponse>> listarClientesInativos(){
        return ResponseEntity.ok(clienteService.listarClientesInativos());
    }

    @Operation(
            summary = "Ativar Cliente",
            description = "Ativa um Cliente inativo com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente ativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PatchMapping("/{idCliente}/ativar")
    public ResponseEntity<ClienteDTO.ClienteResponse> ativarCliente(@PathVariable String idCliente){
        clienteService.ativarCliente(idCliente);
        return ResponseEntity.ok(clienteService.buscarPorId(idCliente));
    }

    @Operation(
            summary = "Inativar Cliente",
            description = "Inativa um Cliente ativo com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente inativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PatchMapping("{idCliente}/inativar")
    public ResponseEntity<ClienteDTO.ClienteResponse> inativarCliente(@PathVariable String idCliente){
        clienteService.inativarCliente(idCliente);
        return ResponseEntity.ok(clienteService.buscarPorId(idCliente));
    }

    //------------------------------------------------------------------

    // Função especifica de um Ambiente para determinado Cliente
    @Operation(
            summary = "Adicionar Ambiente ao Cliente",
            description = "Adiciona um novo Ambiente a um Cliente específico com base no ID do Cliente fornecido.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Dados do novo ambiente a ser criado e associado ao idCliente.",
                    content = @Content(
                            schema = @Schema(implementation = AmbienteDTO.AmbienteRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nome_ambiente": "Estufa de Verão",
                                        "observacao": "Ambiente para cultivo de tomates.",
                                        "localizao": "Setor Norte, Bloco A",
                                        "estado_ambiente": "ATIVO"
                                    }
                                """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ambiente adicionado ao Cliente com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PostMapping(value = "/{idCliente}/ambiente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AmbienteDTO.AmbienteResponse> adicionarAmbienteAoCliente(
            @PathVariable String idCliente,
            @Valid @RequestPart("dados") AmbienteDTO.AmbienteRequest request,
            @RequestPart(value = "foto") MultipartFile fotoAmbiente
    ) throws IOException {
        AmbienteDTO.AmbienteResponse response = ambienteService.criarAmbienteParaCliente(idCliente, request, fotoAmbiente);

        return ResponseEntity.created(URI.create("/api/cliente/" + idCliente + "/ambientes/" + response.idAmbiente()))
                .body(response);
    }

    @Operation(
            summary = "Listar Ambientes por Cliente",
            description = "Retorna uma lista de todos os Ambientes associados a um Cliente específico com base no ID do Cliente fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de Ambientes retornada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/{idCliente}/ambiente")
    public ResponseEntity<List<AmbienteDTO.AmbienteResponse>> listarAmbientesPorCliente(@PathVariable String idCliente){
        List<AmbienteDTO.AmbienteResponse> response = ambienteService.listarAmbientesPorCliente(idCliente);
        return ResponseEntity.ok(response);
    }

    //---------------------------------------------------------------------------
}