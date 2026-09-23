package com.senai.FloraSaaS.domain.exception.usuario;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String id_cliente) {
        super("Cliente com Id: " + id_cliente + "não encontrado");
    }
}