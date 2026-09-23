package com.senai.FloraSaaS.domain.exception.usuario;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String id_usuario) {
        super("Usuário com Id: " + id_usuario + " não encontrado");
    }
}