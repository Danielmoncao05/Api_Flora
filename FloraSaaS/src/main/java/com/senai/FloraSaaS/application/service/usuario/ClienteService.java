package com.senai.FloraSaaS.application.service.usuario;

import com.senai.FloraSaaS.application.dto.usuario.ClienteDTO;
import com.senai.FloraSaaS.domain.entity.usuario.Cliente;
import com.senai.FloraSaaS.domain.repository.usuario.ClienteRepository;
import com.senai.FloraSaaS.domain.exception.usuario.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    //CRUD normal
    public ClienteDTO.ClienteResponse criarCliente(ClienteDTO.ClienteRequest request){
        Cliente cliente = request.toEntity();
        cliente.setSenha(passwordEncoder.encode(request.senhaCliente()));

        return ClienteDTO.ClienteResponse.fromEntity(clienteRepository.save(cliente));
    }

    public List<ClienteDTO.ClienteResponse> listarClientes(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteDTO.ClienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ClienteDTO.ClienteResponse buscarPorId(String idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        return ClienteDTO.ClienteResponse.fromEntity(cliente);
    }

    public ClienteDTO.ClienteResponse atualizarCliente(String idCliente, ClienteDTO.ClienteRequest request){
        Cliente existente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        existente.setNome(request.nomeCliente());
        existente.setDataNascimento(request.dataNascimentoCliente());
        existente.setEmail(request.emailCliente());

        if (request.senhaCliente() != null && !request.senhaCliente().isBlank()) {
            existente.setSenha(passwordEncoder.encode(request.senhaCliente()));
        }

        return ClienteDTO.ClienteResponse.fromEntity(clienteRepository.save(existente));
    }

    public void excluirCliente(String idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        clienteRepository.delete(cliente);
    }

    //----------------------------------------------------------------

    // Funcoes relacionadas a ativar/inativar
    public void ativarCliente(String idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        cliente.setAtivo(true);
        clienteRepository.save(cliente);
    }

    public void inativarCliente(String idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public List<ClienteDTO.ClienteResponse> listarClientesAtivos(){
        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(ClienteDTO.ClienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ClienteDTO.ClienteResponse> listarClientesInativos(){
        return clienteRepository.findByAtivoFalse()
                .stream()
                .map(ClienteDTO.ClienteResponse::fromEntity)
                .collect(Collectors.toList());
    }

}