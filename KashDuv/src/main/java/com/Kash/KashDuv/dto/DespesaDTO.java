package com.Kash.KashDuv.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DespesaDTO {

    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "Valor não pode ser nulo")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
    private BigDecimal valor;

    @NotBlank(message = "Categoria não pode estar vazia")
    private String categoria;

    @NotNull(message = "Data não pode ser nula")
    @PastOrPresent(message = "Data não pode ser no futuro")
    private LocalDate data;
}