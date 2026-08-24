package com.Kash.KashDuv.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ResumoFinanceiroDTO(BigDecimal totalReceitas, BigDecimal totalDespesas,
                                  BigDecimal saldo, Map<String, BigDecimal> despesasPorCategoria,
                                  Map<String, BigDecimal> receitasPorCategoria) {
}
