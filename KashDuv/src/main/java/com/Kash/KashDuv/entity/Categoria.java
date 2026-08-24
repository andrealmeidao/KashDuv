package com.Kash.KashDuv.entity;

import java.text.Normalizer;
import java.util.Arrays;

public enum Categoria {
    ALIMENTACAO, TRANSPORTE, MORADIA, SAUDE, LAZER, EDUCACAO,
    SALARIO, INVESTIMENTOS, OUTROS;

    public static boolean valida(String valor) {
        if (valor == null || valor.isBlank()) return false;
        String normalizada = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toUpperCase();
        return Arrays.stream(values()).anyMatch(categoria -> categoria.name().equals(normalizada));
    }

    public static String normaliza(String valor) {
        if (!valida(valor)) throw new IllegalArgumentException("Categoria inválida");
        return Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toUpperCase();
    }
}