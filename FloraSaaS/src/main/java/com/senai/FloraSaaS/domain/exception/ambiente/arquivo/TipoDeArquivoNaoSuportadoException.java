package com.senai.FloraSaaS.domain.exception.ambiente.arquivo;

public class TipoDeArquivoNaoSuportadoException extends RuntimeException {
    public TipoDeArquivoNaoSuportadoException(String message) {
        super(message);
    }
}