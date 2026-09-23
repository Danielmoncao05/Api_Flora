package com.senai.FloraSaaS.domain.enums.ambiente;

public enum CategoriaAmbiente {
    FLORES("Flores e Plantas com Flor"),
    FOLHAGENS("Plantas de Folhagem (Interiores)"),
    HORTALICAS("Hortaliças e Vegetais"),
    FRUTIFERAS("Plantas Frutíferas"),
    ERVAS_E_TEMPEROS("Ervas Aromáticas e Temperos"),
    SUCULENTAS_E_CACTOS("Suculentas e Cactos"),
    MEDICINAL("Plantas Medicinais");

    private final String descricao;

    CategoriaAmbiente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}