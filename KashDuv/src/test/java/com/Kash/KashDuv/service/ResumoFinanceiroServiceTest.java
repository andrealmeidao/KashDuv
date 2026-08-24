package com.Kash.KashDuv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import com.Kash.KashDuv.repository.CategoriaTotal;
import com.Kash.KashDuv.repository.DespesaRepository;
import com.Kash.KashDuv.repository.ReceitaRepository;

class ResumoFinanceiroServiceTest {
    private final ReceitaRepository receitas = Mockito.mock(ReceitaRepository.class);
    private final DespesaRepository despesas = Mockito.mock(DespesaRepository.class);
    private final ResumoFinanceiroService service = new ResumoFinanceiroService(receitas, despesas);

    @Test
    void calculaTotaisESeparaCategorias() {
        LocalDate inicio = LocalDate.of(2026, 8, 1); LocalDate fim = LocalDate.of(2026, 8, 31);
        when(receitas.totalNoPeriodo("ana", inicio, fim)).thenReturn(new BigDecimal("1000"));
        when(despesas.totalNoPeriodo("ana", inicio, fim)).thenReturn(new BigDecimal("250"));
        when(receitas.totaisPorCategoria("ana", inicio, fim)).thenReturn(List.of(projecao("SALARIO", "1000")));
        when(despesas.totaisPorCategoria("ana", inicio, fim)).thenReturn(List.of(projecao("MORADIA", "250")));
        var resultado = service.resumir(2026, 8, "ana");
        assertEquals(new BigDecimal("750"), resultado.saldo());
        assertEquals(new BigDecimal("250"), resultado.despesasPorCategoria().get("MORADIA"));
        assertEquals(new BigDecimal("1000"), resultado.receitasPorCategoria().get("SALARIO"));
    }

    private CategoriaTotal projecao(String categoria, String total) {
        return new CategoriaTotal() { public String getCategoria() { return categoria; } public BigDecimal getTotal() { return new BigDecimal(total); } };
    }
}