package com.senai.FloraSaaS.application.dto.ambiente;

import com.senai.FloraSaaS.application.dto.sensor.SensorDTO;
import com.senai.FloraSaaS.domain.entity.ambiente.Ambiente;
import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.enums.ambiente.StatusAmbiente;
import com.senai.FloraSaaS.domain.enums.ambiente.CategoriaAmbiente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

public class AmbienteDTO {
    public record AmbienteRequest(
            @NotBlank(message = "Nome do Ambiente é obrigatório")
            @Schema(description = "Nome do Ambiente", examples = "Estufa Principal")
            String nomeAmbiente,

            @NotBlank(message = "Descrição é obrigatória")
            @Schema(description = "Descrição do Ambiente", examples = "Ambiente com alta umidade")
            String descricao,

            @NotBlank(message = "Localização é obrigatória")
            @Schema(description = "Localização do Ambiente", examples = "Setor A - Praça Central")
            String localizacao,

            @NotBlank(message = "Foto do Ambiente é obrigatória")
            @Schema(description = "Foto do Ambiente", examples = "base64encodedstring...")
            String fotoAmbiente,

            @NotNull(message = "Categoria do Ambiente é obrigatória")
            @Schema(description = "Categoria do Ambiente", examples = "ESTUFA")
            CategoriaAmbiente categoria,

            @NotNull(message = "Estado do Ambiente é obrigatório") // todo -> alterar @NotNull em List's, Enum's etc
            @Schema(description = "Estado do Ambiente", examples = "SAUDAVEL")
            StatusAmbiente statusAmbiente,

            @NotBlank(message = "Cliente é obrigatório")
            @Schema(description = "Cliente dono do Ambiente", example = "c1d2e3f4-g5h6-7i8j-9k0l-m1n2o3p4q5r6")
            Cliente idCliente,

            @NotEmpty(message = "Lista de Sensores é obrigatória")
            @Schema(description = "Lista de IDs de Sensores no Ambiente", examples = "[\"s1d2f3g4-h5j6-k7l8-m9n0-o1p2q3r4s5t6\", \"s7d8f9g0-h1j2-k3l4-m5n6-o7p8q9r0s1t2\"]")
            List<String> sensoresIds
    ){
        public Ambiente toEntity(){
            return Ambiente.builder()
                    .nomeAmbiente(this.nomeAmbiente)
                    .descricao(this.descricao)
                    .localizacao(this.localizacao)
                    .fotoAmbiente(this.fotoAmbiente)
                    .categoria(this.categoria)
                    .statusAmbiente(this.statusAmbiente)
                    .ativo(true)
                    .cliente(this.idCliente)
                    .sensoresIds(this.sensoresIds)
                    .build();
        }
    }

    @Builder
    public record AmbienteResponse(
            @Schema(description = "ID do Ambiente", example = "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6")
            String idAmbiente,

            @Schema(description = "Nome do Ambiente", examples = "Estufa Principal")
            String nomeAmbiente,

            @Schema(description = "Descrição do Ambiente", examples = "Ambiente com alta umidade")
            String descricao,

            @Schema(description = "Localização do Ambiente", examples = "Setor A - Praça Central")
            String localizacao,

            boolean ativo,

            @Schema(description = "Foto do Ambiente", examples = "base64encodedstring...")
            String fotoAmbiente,

            @Schema(description = "Categoria do Ambiente", examples = "ESTUFA")
            CategoriaAmbiente categoria,

            @Schema(description = "Estado do Ambiente", examples = "ATIVO")
            StatusAmbiente statusAmbiente,

            @Schema(description = "ID do Cliente dono do Ambiente", example = "c1d2e3f4-g5h6-7i8j-9k0l-m1n2o3p4q5r6")
            String idCliente,

            @Schema(description = "Lista de Sensores no Ambiente", examples = "[{...}, {...}]")
            List<SensorDTO.SensorResponse> sensores
    ){
        public static AmbienteResponse fromEntity(Ambiente ambiente){
            if(ambiente == null) return null;

            List<SensorDTO.SensorResponse> listaSensores = ambiente.getSensoresIds() != null
                    ? ambiente.getSensoresIds().stream()
                    .map(sensorId -> SensorDTO.SensorResponse.builder()
                            .idSensor(sensorId)
                            .build())
                    .toList() : List.of();

            return AmbienteResponse.builder()
                    .idAmbiente(ambiente.getIdAmbiente())
                    .nomeAmbiente(ambiente.getNomeAmbiente())
                    .descricao(ambiente.getDescricao())
                    .localizacao(ambiente.getLocalizacao())
                    .statusAmbiente(ambiente.getStatusAmbiente())
                    .ativo(ambiente.isAtivo())
                    .idCliente(ambiente.getCliente() != null ? ambiente.getCliente().getIdUsuario() : null)

                    .sensores(listaSensores)
                    .build();
        }
    }

}