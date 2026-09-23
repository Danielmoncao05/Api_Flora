package com.senai.FloraSaaS.domain.enums.planta;

import com.senai.FloraSaaS.domain.enums.ambiente.CategoriaAmbiente;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Especie {
    // --- FRUTIFERAS ---
    ABACATEIRO(CategoriaAmbiente.FRUTIFERAS),
    ABACAXI(CategoriaAmbiente.FRUTIFERAS),
    ACAI(CategoriaAmbiente.FRUTIFERAS),
    AMOREIRA(CategoriaAmbiente.FRUTIFERAS),
    BANANEIRA(CategoriaAmbiente.FRUTIFERAS),
    CEREJEIRA(CategoriaAmbiente.FRUTIFERAS),
    JABUTICABEIRA(CategoriaAmbiente.FRUTIFERAS),
    LARANJEIRA(CategoriaAmbiente.FRUTIFERAS),
    MORANGUEIRO(CategoriaAmbiente.FRUTIFERAS),
    PARREIRA(CategoriaAmbiente.FRUTIFERAS),
    URUCUM(CategoriaAmbiente.FRUTIFERAS),

    // --- HORTALICAS ---
    AGRIAO(CategoriaAmbiente.HORTALICAS),
    ALCACHOFRA(CategoriaAmbiente.HORTALICAS),
    ALFACE(CategoriaAmbiente.HORTALICAS),
    CHICORIA(CategoriaAmbiente.HORTALICAS),
    ESPINAFRE(CategoriaAmbiente.HORTALICAS),
    MOSTARDA(CategoriaAmbiente.HORTALICAS),
    ORA_PRO_NOBIS(CategoriaAmbiente.HORTALICAS),
    PEPINO(CategoriaAmbiente.HORTALICAS),
    RABANETE(CategoriaAmbiente.HORTALICAS),
    TOMATEIRO(CategoriaAmbiente.HORTALICAS),

    // --- ERVAS_E_TEMPEROS ---
    ALHO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    CAMOMILA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    CIDREIRA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    ERVA_CIDREIRA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    ERVA_DE_GATO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    ERVA_MATE(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    FUNCHO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    HORTELA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    LOUREIRO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    MANJERICAO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    MANJERONA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    OREGANO(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    SALSA(CategoriaAmbiente.ERVAS_E_TEMPEROS),
    SALVIA(CategoriaAmbiente.ERVAS_E_TEMPEROS),

    // --- FLORES ---
    ANTURIO(CategoriaAmbiente.FLORES),
    BEGONIA_MACULATA(CategoriaAmbiente.FLORES),
    BOCA_DE_LEAO(CategoriaAmbiente.FLORES),
    DALIA(CategoriaAmbiente.FLORES),
    FLAMBOYANT(CategoriaAmbiente.FLORES),
    GARDENIA(CategoriaAmbiente.FLORES),
    GAURA(CategoriaAmbiente.FLORES),
    GAZANIA(CategoriaAmbiente.FLORES),
    GENGIBRE_MAGNIFICO(CategoriaAmbiente.FLORES),
    GIRASSOL(CategoriaAmbiente.FLORES),
    HELICONIA(CategoriaAmbiente.FLORES),
    HIBISCO(CategoriaAmbiente.FLORES),
    HORTENSIA(CategoriaAmbiente.FLORES),
    IPE(CategoriaAmbiente.FLORES),
    JACOBINIA(CategoriaAmbiente.FLORES),
    JASMIN(CategoriaAmbiente.FLORES),
    LIRIO(CategoriaAmbiente.FLORES),
    LIRIO_DA_PAZ(CategoriaAmbiente.FLORES),
    LOTUS(CategoriaAmbiente.FLORES),
    MADRESSILVA(CategoriaAmbiente.FLORES),
    MAGNOLIA(CategoriaAmbiente.FLORES),
    MARGARIDA(CategoriaAmbiente.FLORES),
    NARCISO(CategoriaAmbiente.FLORES),
    NINFEIA(CategoriaAmbiente.FLORES),
    ORQUIDEA(CategoriaAmbiente.FLORES),
    QUARESMEIRA(CategoriaAmbiente.FLORES),
    ROSEIRA(CategoriaAmbiente.FLORES),
    TULIPA(CategoriaAmbiente.FLORES),
    VANDA(CategoriaAmbiente.FLORES),
    VERBERA(CategoriaAmbiente.FLORES),
    VEU_DE_NOIVA(CategoriaAmbiente.FLORES),
    VIOLETA(CategoriaAmbiente.FLORES),
    VIOLETA_AFRICANA(CategoriaAmbiente.FLORES),
    VINCA(CategoriaAmbiente.FLORES),
    VITORIA_REGIA(CategoriaAmbiente.FLORES),

    // --- FOLHAGENS ---
    AVENCA(CategoriaAmbiente.FOLHAGENS),
    BAMBU(CategoriaAmbiente.FOLHAGENS),
    COSTELA_DE_ADAO(CategoriaAmbiente.FOLHAGENS),
    ESPADA_DE_SAO_JORGE(CategoriaAmbiente.FOLHAGENS),
    FICUS(CategoriaAmbiente.FOLHAGENS),
    HERA(CategoriaAmbiente.FOLHAGENS),
    JIBOIA(CategoriaAmbiente.FOLHAGENS),
    JUNCO(CategoriaAmbiente.FOLHAGENS),
    LAMBARI(CategoriaAmbiente.FOLHAGENS),
    MARANTA(CategoriaAmbiente.FOLHAGENS),
    PALMEIRA(CategoriaAmbiente.FOLHAGENS),
    PALMEIRA_RAFIS(CategoriaAmbiente.FOLHAGENS),
    PATA_DE_ELEFANTE(CategoriaAmbiente.FOLHAGENS),
    SAMAMBAIA(CategoriaAmbiente.FOLHAGENS),
    SAMAMBAIA_AMERICANA(CategoriaAmbiente.FOLHAGENS),
    TREVO(CategoriaAmbiente.FOLHAGENS),
    XAXIM(CategoriaAmbiente.FOLHAGENS),
    ZAMIOCULCA(CategoriaAmbiente.FOLHAGENS),

    // --- SUCULENTAS_E_CACTOS ---
    CACTO(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    COLAR_DE_PEROLAS(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ESTRELINHA(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    JADE(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ORELHA_DE_COELHO(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ORELHA_DE_ELEFANTE(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ORELHA_DE_SHREK(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    PLANTA_FANTASMA(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    RABO_DE_BURRO(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ROSA_DE_PEDRA(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ROSINHA_DE_SOL(CategoriaAmbiente.SUCULENTAS_E_CACTOS),
    ZEBRA(CategoriaAmbiente.SUCULENTAS_E_CACTOS),

    // --- MEDICINAIS ---
    // (Muitas foram movidas para Ervas, Flores ou Hortaliças)
    ARNICA(CategoriaAmbiente.MEDICINAL),
    ARRUDA(CategoriaAmbiente.MEDICINAL),
    SAIAO(CategoriaAmbiente.MEDICINAL),
    SANDALO(CategoriaAmbiente.MEDICINAL),
    VASSOURA(CategoriaAmbiente.MEDICINAL);

    // --- Campos, Construtor e Métodos ---
    private final CategoriaAmbiente categoria;

    Especie(CategoriaAmbiente categoria) {
        this.categoria = categoria;
    }

    public CategoriaAmbiente getCategoria() {
        return this.categoria;
    }

    public static Set<Especie> getEspeciesPorCategoria(CategoriaAmbiente categoria) {
        // EnumSet é otimizado para enums
        return EnumSet.allOf(Especie.class)
                .stream()
                .filter(especie -> especie.getCategoria() == categoria)
                .collect(Collectors.toSet());
    }

}