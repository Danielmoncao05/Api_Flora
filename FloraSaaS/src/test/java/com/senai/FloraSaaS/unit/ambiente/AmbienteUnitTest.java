package com.senai.FloraSaaS.unit.ambiente;

import com.senai.FloraSaaS.application.dto.ambiente.AmbienteDTO;
import com.senai.FloraSaaS.application.service.ambiente.AmbienteService;
import com.senai.FloraSaaS.domain.entity.ambiente.Ambiente;
import com.senai.FloraSaaS.domain.enums.ambiente.StatusAmbiente;
import com.senai.FloraSaaS.domain.exception.ambiente.ValidacaoAmbienteException;
import com.senai.FloraSaaS.domain.exception.ambiente.AmbienteNaoEncontradoException;
import com.senai.FloraSaaS.domain.repository.ambiente.AmbienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AmbienteUnitTest {
    @Mock
    private AmbienteRepository repository;

    @InjectMocks
    private AmbienteService service;

    @Test
    void deveSalvarAmbienteValido(){
        AmbienteDTO.AmbienteRequest dto = new AmbienteDTO.AmbienteRequest(
                "Sala de Germinação",
                "Setor A - Bloco 1",
                "Ambiente climatizado usado para o cultivo de tomates e alfaces.",
                StatusAmbiente.ATIVO,
                null,
                Collections.emptyList(),
                Collections.emptyList()
        );

        Ambiente entidade = dto.toEntity();

        when(repository.save(any())).thenReturn(entidade);
        AmbienteDTO.AmbienteResponse salvo = service.criarAmbiente(dto);

        assertNotNull(salvo);

        assertEquals("Sala de Germinação", salvo.nome_ambiente());
        assertEquals("Ambiente climatizado usado para o cultivo de tomates e alfaces.",salvo.observacao());
        assertEquals(StatusAmbiente.ATIVO,salvo.estado_ambiente());
        assertEquals("Setor A - Bloco 1",salvo.localizacao());

        verify(repository).save(any());
    }

    @Test
    void deveLancarValidacaoExceptionSeNomeForNulo(){
        Ambiente ambiente = new Ambiente();

        ambiente.setNomeAmbiente(null);
        ambiente.setStatusAmbiente(StatusAmbiente.ATIVO);
        ambiente.setDescricao("Estufa 100% OK");
        ambiente.setLocalizacao("Setor A - Bloco 1");

        ValidacaoAmbienteException ex = assertThrows(
                ValidacaoAmbienteException.class,
                ambiente::validar
        );

        assertEquals("O nome do ambiente deve ser informado.",ex.getMessage());
    }

    @Test
    void deveLancarValidacaoExceptionSeoEstadoForNulo(){

        Ambiente ambiente = new Ambiente();
        ambiente.setNomeAmbiente("Estufa 1");
        ambiente.setStatusAmbiente(null);
        ambiente.setDescricao("Estufa 100% OK");
        ambiente.setLocalizacao("Setor A - Bloco 1");

        ValidacaoAmbienteException ex = assertThrows(
                ValidacaoAmbienteException.class,
                ambiente::validar
        );

        assertEquals("O estado do ambiente deve ser informado, como: (ATIVO) ou (INATIVO)", ex.getMessage());

    }

    @Test
    void deveLancarValidacaoExceptionSeALocalizacaoForNulo(){

        Ambiente ambiente = new Ambiente();
        ambiente.setNomeAmbiente("Estufa 2");
        ambiente.setStatusAmbiente(StatusAmbiente.ATIVO);
        ambiente.setDescricao("Estufa 100% OK");
        ambiente.setLocalizacao(null);

        ValidacaoAmbienteException ex = assertThrows(
                ValidacaoAmbienteException.class,
                ambiente::validar
        );

        assertEquals("A localização do ambiente deve ser informado.", ex.getMessage());

    }

    @Test
    void deveLancarValidacaoExceptionSeaObservacaoForNulo(){
        Ambiente ambiente = new Ambiente();

        ambiente.setNomeAmbiente("Estufa 2");
        ambiente.setStatusAmbiente(StatusAmbiente.ATIVO);
        ambiente.setDescricao(null);
        ambiente.setLocalizacao("Setor 1");

        ValidacaoAmbienteException ex = assertThrows(
                ValidacaoAmbienteException.class,
                ambiente::validar
        );

        assertEquals("A observação do ambiente deve ser informado.",ex.getMessage());
    }

    @Test
    void deveBuscarAmbientePorId(){
        String idAmbiente = "c1d2e3f4-g5h6-7i8j-9k0l-m1n2o3p4q5r6";

        Ambiente ambiente = new Ambiente(
                idAmbiente,
                "Estufa 1",
                "Estufa OK",
                true,
                "Setor 1",
                StatusAmbiente.SAUDAVEL,
                null,
                null,
                null
        );

        when(repository.findById(idAmbiente)).thenReturn(Optional.of(ambiente));

        AmbienteDTO.AmbienteResponse resultado = service.buscarAmbientePorId(idAmbiente);

        assertNotNull(resultado);

        assertEquals(idAmbiente,resultado.idAmbiente()); // Verificação total
        assertEquals("Estufa 1", resultado.nomeAmbiente());
        assertEquals("Estufa OK",resultado.observacao());
        assertEquals("Setor 1",resultado.localizacao());

        verify(repository).findById(idAmbiente);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarIdInexistente(){
        String idAmbiente = "c1d2e3f4-g5h6-7i8j-9k0l-m1n2o3p4q5r6";
        when(repository.findById(idAmbiente)).thenReturn(Optional.empty());

        AmbienteNaoEncontradoException ex = assertThrows(AmbienteNaoEncontradoException.class,
                () -> service.buscarAmbientePorId(idAmbiente));
        assertEquals("Ambiente com ID " + idAmbiente + " não encontrado", ex.getMessage());
    }

}