package com.Kash.KashDuv.controller;

import com.Kash.KashDuv.dto.ResumoFinanceiroDTO;
import com.Kash.KashDuv.service.ResumoFinanceiroService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/resumo")
public class ResumoFinanceiroController {
    private final ResumoFinanceiroService service;
    public ResumoFinanceiroController(ResumoFinanceiroService service) { this.service = service; }
    @GetMapping("/{ano}/{mes}")
    public ResumoFinanceiroDTO resumir(@PathVariable @Min(2000) int ano, @PathVariable @Min(1) @Max(12) int mes, Authentication auth) { return service.resumir(ano, mes, auth.getName()); }
}
