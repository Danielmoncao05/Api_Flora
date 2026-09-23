package com.senai.FloraSaaS.domain.entity.usuario;

import com.senai.FloraSaaS.domain.enums.usuario.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity @SuperBuilder
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String idUsuario;

    @Column(nullable = false)
    protected String nome;

    @Email
    @Column(nullable = false, unique = true, length = 90)
    protected String email;

    @Column(nullable = false)
    protected String senha;

    @Column(nullable = false)
    protected boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;
}