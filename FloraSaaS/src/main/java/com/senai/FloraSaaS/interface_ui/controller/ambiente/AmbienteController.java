package com.senai.FloraSaaS.interface_ui.controller.ambiente;


import com.senai.FloraSaaS.application.dto.ambiente.AmbienteDTO;
import com.senai.FloraSaaS.application.dto.ambiente.EstadoAmbienteDTO;
import com.senai.FloraSaaS.application.service.ambiente.AmbienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/ambiente")
public class AmbienteController {
    private final AmbienteService ambienteService;

    //CRUD normal
    @Operation(
            summary = "Cria um novo Ambiente",
            description = "Cria um novo Ambiente com os dados fornecidos no corpo da requisição.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AmbienteDTO.AmbienteRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                            "nome_ambiente":"Estufa Principal",
                                            "observacao":"Estufa de testes",
                                            "localizao":"Setor 4",
                                            "estado_ambiente":"ATIVO",
                                            "cliente":"60c72b2f-9b1d-4f1e-b3f1-c0f1b0a8f2a1",
                                            "plantas","{...}",
                                            "sensoresIds":"{...}"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ambiente criado com sucesso."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Erro de Validação", value = """
                                            {
                                                "timestamp": "2024-06-16T11:00:00",
                                                "status": 400,
                                                "errors": [
                                                    "O nome do ambiente é obrigatório.",
                                                    "O ID do cliente é obrigatório."
                                                ]
                                            }
                                            """),
                                            @ExampleObject(name = "Valor Inválido", value = """
                                            {
                                                "timestamp": "2024-06-16T11:05:00",
                                                "status": 400,
                                                "message": "Estado do ambiente inválido. Valores permitidos: ATIVO, INATIVO."
                                            }
                                            """)
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AmbienteDTO.AmbienteResponse> criaAmbiente(
            @Valid @RequestPart("dados") AmbienteDTO.AmbienteRequest request,
            @RequestPart(value = "foto_ambiente") MultipartFile fotoAmbiente
    ) throws IOException {
        AmbienteDTO.AmbienteResponse ambienteCriado = ambienteService.criarAmbiente(request, fotoAmbiente);

        return ResponseEntity.created(URI.create("/api/ambiente/".concat(ambienteCriado.idAmbiente())))
                .body(ambienteCriado);
    }

    @Operation(
            summary = "Listar todos os ambientes",
            description = "Retorna uma lista de todos os ambientes cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ambientes retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping
    public ResponseEntity<List<AmbienteDTO.AmbienteResponse>> listarAmbientes(){
        return ResponseEntity.ok(ambienteService.listarAmbientes());
    }

    @Operation(
            summary = "Buscar Ambiente por ID",
            description = "Retorna os detalhes de um Ambiente específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ambiente encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ambiente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/{idAmbiente}")
    public ResponseEntity<AmbienteDTO.AmbienteResponse> buscarAmbientePorId(@PathVariable String idAmbiente){
        return ResponseEntity.ok(ambienteService.buscarAmbientePorId(idAmbiente));
    }

    @Operation(
            summary = "Atualizar ambiente",
            description = "Atualiza os dados de um ambiente existente com base no ID fornecido.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Dados do ambiente para atualização",
                    content = @Content(schema = @Schema(implementation = AmbienteDTO.AmbienteRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ambiente atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ambiente não encontrado"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida (Ex: campo obrigatório nulo)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                        {
                                            "timestamp": "2024-06-16T11:10:00",
                                            "status": 400,
                                            "errors": [
                                                "O nome do ambiente não pode ser vazio."
                                            ]
                                        }
                                        """)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PutMapping(value = "/{idAmbiente}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AmbienteDTO.AmbienteResponse> atualizarAmbiente(
            @PathVariable String idAmbiente,
            @Valid @RequestPart("dados") AmbienteDTO.AmbienteRequest request,
            @RequestPart(value = "foto_ambiente", required = false) MultipartFile fotoAmbiente
    ) throws IOException {
        return ResponseEntity.ok(ambienteService.atualizarAmbiente(idAmbiente, request, fotoAmbiente));
    }

    @Operation(
            summary = "Deletar ambiente",
            description = "Remove um ambiente do sistema com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ambiente deletado com sucesso (Sem conteúdo)"),
                    @ApiResponse(responseCode = "404", description = "Ambiente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @DeleteMapping("/{idAmbiente}")
    public ResponseEntity<Void> excluirAmbiente(@PathVariable String idAmbiente){
        ambienteService.deletarAmbiente(idAmbiente);
        return ResponseEntity.noContent().build();
    }

    //------------------------------------------------------------------------------------

    // Algumas funções relacionadas a ativar/inativar
    @Operation(
            summary = "Listar ambientes ativos",
            description = "Retorna uma lista de todos os ambientes ativos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ambientes ativos retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/ativos")
    public ResponseEntity<List<AmbienteDTO.AmbienteResponse>> listarAmbientesAtivos(){
        return ResponseEntity.ok(ambienteService.listarAmbientesAtivos());
    }

    @Operation(
            summary = "Listar ambientes inativos",
            description = "Retorna uma lista de todos os ambientes inativos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ambientes inativos retornada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @GetMapping("/inativos")
    public ResponseEntity<List<AmbienteDTO.AmbienteResponse>> listarAmbientesInativos(){
        return ResponseEntity.ok(ambienteService.listarAmbientesInativos());
    }

    @Operation(
            summary = "Ativar ambiente",
            description = "Ativa um ambiente inativo com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ambiente ativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ambiente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PatchMapping("{idAmbiente}/ativar")
    public ResponseEntity<AmbienteDTO.AmbienteResponse> ativarAmbiente(@PathVariable String idAmbiente){
        ambienteService.ativarAmbiente(idAmbiente);
        return ResponseEntity.ok(ambienteService.buscarAmbientePorId(idAmbiente));
    }

    @Operation(
            summary = "Inativar ambiente",
            description = "Inativa um ambiente ativo com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ambiente inativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Ambiente não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
                    @ApiResponse(responseCode = "401", description = "Não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Proibido")
            }
    )
    @PatchMapping("/{idAmbiente}/inativar")
    public ResponseEntity<AmbienteDTO.AmbienteResponse> inativarAmbiente(@PathVariable String idAmbiente){
        ambienteService.inativarAmbiente(idAmbiente);
        return ResponseEntity.ok(ambienteService.buscarAmbientePorId(idAmbiente));
    }

    //-------------------------------------------------------------------------------

    @GetMapping("/{idAmbiente}/estado-tempo-real")
    public ResponseEntity<EstadoAmbienteDTO.EstadoAmbienteResponse> obterEstadoTempoReal(@PathVariable String idAmbiente) {
        // Lógica: Vai no banco/IoT Hub, pega os valores AGORA dos sensores deste ambiente
        EstadoAmbienteDTO.EstadoAmbienteResponse estado = ambienteService.obterLeituraAtualSensores(idAmbiente);
        return ResponseEntity.ok(estado);
    }
}