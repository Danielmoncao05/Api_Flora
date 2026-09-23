package com.senai.FloraSaaS.application.dto.ambiente;

import com.senai.FloraSaaS.domain.entity.ambiente.EstadoAmbiente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

public class EstadoAmbienteDTO {
    public record EstadoAmbienteRequest(
            @NotNull(message = "Temperatura é obrigatória")
            @Schema(description = "Temperatura do Ambiente", example = "25.5")
            Double temperatura,

            @NotNull
            @Schema(description = "Umidade do Ambiente", example = "60.0")
            Double umidade,

            @NotNull
            @Schema(description = "Luminosidade do Ambiente", example = "300.0")
            Double luminosidade
    ) {
        public EstadoAmbiente toEntity() {
            return EstadoAmbiente.builder()
                    .temperatura(this.temperatura)
                    .umidade(this.umidade)
                    .luminosidade(this.luminosidade)
                    .dataHoraRegistro(LocalDateTime.now())
                    .build();
        }
    }

    @Builder
    public record EstadoAmbienteResponse(
            @Schema(description = "ID do Estado do Ambiente", example = "e1f2g3h4-i5j6-7k8l-9m0n-o1p2q3r4s5t6")
            String idEstadoAmbiente,

            @Schema(description = "Data e Hora do Registro", example = "2024-06-15T14:30:00")
            LocalDateTime dataHoraRegistro,

            @Schema(description = "Temperatura do Ambiente", example = "25.5")
            Double temperatura,

            @Schema(description = "Umidade do Ambiente", example = "60.0")
            Double umidade,

            @Schema(description = "Luminosidade do Ambiente", example = "300.0")
            Double luminosidade
    ) {
        public static EstadoAmbienteResponse fromEntity(EstadoAmbiente estadoAmbiente) {
            return EstadoAmbienteResponse.builder()
                    .idEstadoAmbiente(estadoAmbiente.getIdEstadoAmbiente())
                    .dataHoraRegistro(estadoAmbiente.getDataHoraRegistro())
                    .temperatura(estadoAmbiente.getTemperatura())
                    .umidade(estadoAmbiente.getUmidade())
                    .luminosidade(estadoAmbiente.getLuminosidade())
                    .build();
        }
    }

}