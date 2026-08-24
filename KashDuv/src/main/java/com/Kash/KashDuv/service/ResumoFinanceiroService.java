package com.Kash.KashDuv.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Kash.KashDuv.dto.ResumoFinanceiroDTO;
import com.Kash.KashDuv.repository.DespesaRepository;
import com.Kash.KashDuv.repository.ReceitaRepository;

@Service
public class ResumoFinanceiroService {
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    public ResumoFinanceiroService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository; this.despesaRepository = despesaRepository;
    }
    public ResumoFinanceiroDTO resumir(int ano, int mes, String usuario) {
        YearMonth periodo = YearMonth.of(ano, mes);
        var inicio = periodo.atDay(1); var fim = periodo.atEndOfMonth();
        BigDecimal totalReceitas = receitaRepository.totalNoPeriodo(usuario, inicio, fim);
        BigDecimal totalDespesas = despesaRepository.totalNoPeriodo(usuario, inicio, fim);
        Map<String, BigDecimal> despesas = new LinkedHashMap<>();
        Map<String, BigDecimal> receitas = new LinkedHashMap<>();
        despesaRepository.totaisPorCategoria(usuario, inicio, fim).forEach(item -> despesas.put(item.getCategoria(), item.getTotal()));
        receitaRepository.totaisPorCategoria(usuario, inicio, fim).forEach(item -> receitas.put(item.getCategoria(), item.getTotal()));
        return new ResumoFinanceiroDTO(totalReceitas, totalDespesas, totalReceitas.subtract(totalDespesas), despesas, receitas);
    }
}
