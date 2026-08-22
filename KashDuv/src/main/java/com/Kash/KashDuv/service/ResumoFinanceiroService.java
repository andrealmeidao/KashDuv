package com.Kash.KashDuv.service;

import com.Kash.KashDuv.dto.ResumoFinanceiroDTO;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ResumoFinanceiroService {
    private final ReceitaService receitaService; private final DespesaService despesaService;
    public ResumoFinanceiroService(ReceitaService receitaService, DespesaService despesaService) { this.receitaService = receitaService; this.despesaService = despesaService; }
    public ResumoFinanceiroDTO resumir(int ano, int mes, String usuario) {
        YearMonth p = YearMonth.of(ano, mes); Map<String, BigDecimal> categorias = new LinkedHashMap<>();
        var receitas = receitaService.listarNoPeriodo(usuario, p.atDay(1), p.atEndOfMonth());
        var despesas = despesaService.listarNoPeriodo(usuario, p.atDay(1), p.atEndOfMonth());
        BigDecimal totalReceitas = receitas.stream().map(r -> r.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDespesas = despesas.stream().map(d -> d.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
        receitas.forEach(r -> categorias.merge("RECEITA:" + r.getCategoria(), r.getValor(), BigDecimal::add));
        despesas.forEach(d -> categorias.merge("DESPESA:" + d.getCategoria(), d.getValor(), BigDecimal::add));
        return new ResumoFinanceiroDTO(totalReceitas, totalDespesas, totalReceitas.subtract(totalDespesas), categorias);
    }
}
