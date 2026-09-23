package com.senai.FloraSaaS.domain.entity.ambiente;

import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.enums.ambiente.StatusAmbiente;
import com.senai.FloraSaaS.domain.enums.ambiente.CategoriaAmbiente;
import com.senai.FloraSaaS.domain.exception.ambiente.ValidacaoAmbienteException;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Ambiente {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idAmbiente;

    @Column(nullable = false, length = 150)
    private String nomeAmbiente;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, unique = true)
    private boolean ativo = true;

    @Column(nullable = false, length = 200)
    private String localizacao;

    @Column(length = 500, nullable = false)
    private String fotoAmbiente;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CategoriaAmbiente categoria;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private StatusAmbiente statusAmbiente;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Armazena apenas os IDs dos sensoresIds (não uma relação JPA)
    @ElementCollection
    @CollectionTable(
            name = "ambiente_sensores",
            joinColumns = @JoinColumn(name = "id_ambiente"))
    @Column(name = "sensor_id", nullable = false)
    private List<String> sensoresIds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ambiente", nullable = false)
    private List<EstadoAmbiente> historicoEstado;

    public void validar() {
        if (nomeAmbiente == null || nomeAmbiente.isBlank()) {
            throw new ValidacaoAmbienteException("O nome do ambiente deve ser informado.");
        }

        if (statusAmbiente == null){
            throw new ValidacaoAmbienteException("O estado do ambiente deve ser informado, como: (ATIVO) ou (INATIVO)");
        }

        if (descricao == null || descricao.isBlank()){
            throw new ValidacaoAmbienteException("A observação do ambiente deve ser informado.");
        }

        if (localizacao == null || localizacao.isBlank()){
            throw new ValidacaoAmbienteException("A localização do ambiente deve ser informado.");
        }
    }

}