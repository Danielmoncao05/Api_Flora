package com.senai.FloraSaaS.application.service.ambiente;

import com.senai.FloraSaaS.application.dto.ambiente.AmbienteDTO;
import com.senai.FloraSaaS.application.dto.ambiente.EstadoAmbienteDTO;
import com.senai.FloraSaaS.application.service.arquivo.FileStorageService;
import com.senai.FloraSaaS.domain.entity.ambiente.Ambiente;
import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.repository.ambiente.AmbienteRepository;
import com.senai.FloraSaaS.domain.repository.usuario.ClienteRepository;
import com.senai.FloraSaaS.domain.exception.ambiente.AmbienteNaoEncontradoException;
import com.senai.FloraSaaS.domain.exception.usuario.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmbienteService {
    private final AmbienteRepository ambienteRepository;
    private final ClienteRepository clienteRepository;
    private final FileStorageService fileStorageService;

    //CRUD normal
    public AmbienteDTO.AmbienteResponse criarAmbiente(
            AmbienteDTO.AmbienteRequest request,
            MultipartFile fotoAmbiente
    ) throws IOException {
        Ambiente ambiente = request.toEntity();

        if(fotoAmbiente != null && !fotoAmbiente.isEmpty()) {
            ambiente.setFotoAmbiente(fileStorageService.storeFile(fotoAmbiente));
        }

        return AmbienteDTO.AmbienteResponse.fromEntity(ambienteRepository.save(ambiente));
    }

    public List<AmbienteDTO.AmbienteResponse> listarAmbientes(){
        return ambienteRepository.findAll()
                .stream()
                .map(AmbienteDTO.AmbienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public AmbienteDTO.AmbienteResponse buscarAmbientePorId(String idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        return AmbienteDTO.AmbienteResponse.fromEntity(ambiente);
    }

    public AmbienteDTO.AmbienteResponse atualizarAmbiente(
            String idAmbiente,
            AmbienteDTO.AmbienteRequest request,
            MultipartFile fotoAmbiente
    ) throws IOException {
        Ambiente existente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        existente.setNomeAmbiente(request.nomeAmbiente());
        existente.setDescricao(request.descricao());
        existente.setLocalizacao(request.localizacao());

        if(fotoAmbiente != null && !fotoAmbiente.isEmpty()) {
            existente.setFotoAmbiente(fileStorageService.storeFile(fotoAmbiente));
        }

        return AmbienteDTO.AmbienteResponse.fromEntity(ambienteRepository.save(existente));
    }

    public void deletarAmbiente(String idAmbiente
    ){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        ambienteRepository.delete(ambiente);
    }

    //------------------------------------------------------------------------------

    // Funcao especifica para ambiente e idCliente
    @Transactional
    public AmbienteDTO.AmbienteResponse criarAmbienteParaCliente(
            String idCliente,
            AmbienteDTO.AmbienteRequest request,
            MultipartFile fotoAmbiente
    ) throws IOException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        Ambiente ambiente = request.toEntity();
        ambiente.setCliente(cliente);

        if(fotoAmbiente != null && !fotoAmbiente.isEmpty()) {
            ambiente.setFotoAmbiente(fileStorageService.storeFile(fotoAmbiente));
        }

        return AmbienteDTO.AmbienteResponse.fromEntity(ambienteRepository.save(ambiente));
    }

    //------------------------------------------------------------------------------------

    // Funcoes de diferentes formas de listagem
    public List<AmbienteDTO.AmbienteResponse> listarAmbientesAtivos(){
        return ambienteRepository.findByAtivoTrue()
                .stream()
                .map(AmbienteDTO.AmbienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AmbienteDTO.AmbienteResponse> listarAmbientesInativos(){
        return ambienteRepository.findByAtivoFalse()
                .stream()
                .map(AmbienteDTO.AmbienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AmbienteDTO.AmbienteResponse> listarAmbientesPorCliente(String idCliente){
        return ambienteRepository.findByCliente_IdUsuario(idCliente)
                .stream()
                .map(AmbienteDTO.AmbienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    //----------------------------------------------------------------------------------------

    // Funcoes de ativar/inativar
    public void ativarAmbiente(String idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        ambiente.setAtivo(true);
        ambienteRepository.save(ambiente);
    }

    public void inativarAmbiente(String idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        ambiente.setAtivo(false);
        ambienteRepository.save(ambiente);
    }

    //----------------------------------------------------------------------------------------
    // Funções para o front consumir
    public EstadoAmbienteDTO.EstadoAmbienteResponse obterLeituraAtualSensores(String idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new AmbienteNaoEncontradoException(idAmbiente));

        List<EstadoAmbienteDTO.EstadoAmbienteResponse> historicoEstados = ambiente.getHistoricoEstado()
                .stream()
                .map(EstadoAmbienteDTO.EstadoAmbienteResponse::fromEntity)
                .toList();

        if(historicoEstados.isEmpty()){
            throw new AmbienteNaoEncontradoException("Nenhum histórico registrado para o ambiente com ID: " + idAmbiente);
        }

        return historicoEstados.getLast();
    }

}