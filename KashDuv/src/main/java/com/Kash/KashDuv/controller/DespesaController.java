package com.Kash.KashDuv.controller;

import com.Kash.KashDuv.dto.DespesaDTO;
import com.Kash.KashDuv.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {
    private final DespesaService service;
    public DespesaController(DespesaService service) { this.service = service; }
    @PostMapping public ResponseEntity<DespesaDTO> criar(@Valid @RequestBody DespesaDTO dto, Authentication auth) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto, auth.getName())); }
    @GetMapping public Page<DespesaDTO> listar(@RequestParam(required = false) String descricao, @RequestParam(required = false) String categoria, @RequestParam(required = false) Integer ano, @RequestParam(required = false) Integer mes, Pageable pageable, Authentication auth) { return service.listar(descricao, categoria, ano, mes, pageable, auth.getName()); }
    @GetMapping("/{id}") public DespesaDTO buscar(@PathVariable String id, Authentication auth) { return service.buscarPorId(id, auth.getName()); }
    @PutMapping("/{id}") public DespesaDTO atualizar(@PathVariable String id, @Valid @RequestBody DespesaDTO dto, Authentication auth) { return service.atualizar(id, dto, auth.getName()); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deletar(@PathVariable String id, Authentication auth) { service.deletar(id, auth.getName()); }
}
