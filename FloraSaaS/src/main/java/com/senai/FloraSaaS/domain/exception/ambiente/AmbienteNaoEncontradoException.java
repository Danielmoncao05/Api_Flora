package com.senai.FloraSaaS.domain.exception.ambiente;

public class AmbienteNaoEncontradoException extends RuntimeException {
    public AmbienteNaoEncontradoException(String id_ambiente) {
        super("Ambiente com Id: " + id_ambiente + " não encontrado");
    }
}