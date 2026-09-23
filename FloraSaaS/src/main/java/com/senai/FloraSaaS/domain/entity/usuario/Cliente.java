package com.senai.FloraSaaS.domain.entity.usuario;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity @SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Cliente extends Usuario {
    @JoinTable(
            name = "data_nascimento",
            joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "idUsuario")
    ) // todo -> testar visualização no banco de dados
    private LocalDate dataNascimento;
}