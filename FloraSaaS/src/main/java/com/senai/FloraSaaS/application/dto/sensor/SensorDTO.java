package com.senai.FloraSaaS.application.dto.sensor;

import com.senai.FloraSaaS.domain.enums.sensor.TipoSensor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class SensorDTO {
    public record SensorRequest(
            @NotBlank(message = "O nomePlanta do sensor é obrigatório.")
            @Schema(description = "Nome do Sensor", example = "Sensor de Temperatura da Estufa")
            String nomeSensor,

            @NotNull(message = "A data de atribuição é obrigatória.")
            @Schema(description = "Data de Atribuição do Sensor", example = "2024-06-15T10:15:30")
            LocalDateTime dataAtribuicao,

            @NotBlank(message = "O código do dispositivo IoT é obrigatório.")
            @Schema(description = "Código do Dispositivo IoT", example = "ESP32-01")
            String codigoDispositivo,

            @NotBlank(message = "O ID do ambiente é obrigatório.")
            @Schema(description = "ID do Ambiente do Sensor", example = "{...}")
            String idAmbiente,

            @NotEmpty(message = "A lista de IDs de notificações é obrigatória.")
            @Schema(description = "Lista de IDs de Notificações do Sensor", example = "[\"n1\", \"n2\"]")
            List<String> notificacoesIds,

            @NotEmpty(message = "A lista de IDs de medidas é obrigatória.")
            @Schema(description = "Lista de IDs de Medidas do Sensor", example = "[\"m1\", \"m2\"]")
            List<String> medidasIds,

            @NotNull(message = "O tipo do sensor é obrigatório.")
            @Schema(description = "Tipo do Sensor", example = "TEMPERATURA")
            TipoSensor tipoSensor
    ) {}

    @Builder
    public record SensorResponse(
            @Schema(description = "ID do Sensor", example = "s1d2f3g4-h5j6-k7l8-m9n0-o1p2q3r4s5t6")
            String idSensor,

            @Schema(description = "Nome do Sensor", example = "Sensor de Temperatura da Estufa")
            String nomeSensor,

            @Schema(description = "Data de Atribuição do Sensor", example = "2024-06-15T10:15:30")
            LocalDateTime dataAtribuicao,

            @Schema(description = "Código do Dispositivo IoT", example = "ESP32-01")
            String codigoDispositivo,

            boolean ativo,

            @Schema(description = "Resumo do Ambiente associado ao Sensor")
            AmbienteResumo ambiente,

            @Schema(description = "Notificações associadas ao Sensor")
            List<NotificacaoResumo> notificacoes,

            @Schema(description = "Medidas associadas ao Sensor")
            List<MedidaResumo> medidas,

            @Schema(description = "Tipo do Sensor", example = "TEMPERATURA")
            TipoSensor tipoSensor
    ) {}

    // Record independente, agora sem métódo fromEntity que depende da classe Ambiente
    public record AmbienteResumo(
            @Schema(description = "ID do Ambiente", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
            String idAmbiente,

            @Schema(description = "Nome do Ambiente", example = "Estufa Principal")
            String nomeAmbiente
    ) {}

    // Record's que referenciam da outra API
    public record NotificacaoResumo(
            @Schema(description = "ID da Notificação", example = "n1n2n3")
            String idNotificacao,

            @Schema(description = "Mensagem da Notificação", example = "Serviço Indisponível")
            String mensagem
    ) {}

    public record MedidaResumo(
            @Schema(description = "ID da Medida", example = "m1m2m3")
            String idMedida,

            @Schema(description = "Temperatura em Celsius", example = "25.5")
            Double temperatura,

            @Schema(description = "Umidade em Porcentagem", example = "70.0")
            Double umidade,

            @Schema(description = "Luminosidade em Porcentagem", example = "48.0")
            Double luminosidade
    ) {}

}