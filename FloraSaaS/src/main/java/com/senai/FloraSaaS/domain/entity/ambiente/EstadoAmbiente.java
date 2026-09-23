package com.senai.FloraSaaS.domain.entity.ambiente;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EstadoAmbiente {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idEstadoAmbiente;

    @Column(nullable = false)
    private LocalDateTime dataHoraRegistro;

    @Column(nullable = false)
    private Double temperatura;

    @Column(nullable = false)
    private Double umidade;

    @Column(nullable = false)
    private Double luminosidade;
}