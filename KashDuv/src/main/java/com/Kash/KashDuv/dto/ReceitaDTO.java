package com.Kash.KashDuv.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ReceitaDTO {
    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private LocalDate data;
}