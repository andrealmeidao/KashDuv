package com.Kash.KashDuv.controller;

import com.Kash.KashDuv.dto.ReceitaDTO;
import com.Kash.KashDuv.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {
    private final ReceitaService service;
    public ReceitaController(ReceitaService service) { this.service = service; }
    @PostMapping public ResponseEntity<ReceitaDTO> criar(@Valid @RequestBody ReceitaDTO dto, Authentication auth) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto, auth.getName())); }
    @GetMapping public Page<ReceitaDTO> listar(@RequestParam(required = false) String descricao, @RequestParam(required = false) String categoria, @RequestParam(required = false) Integer ano, @RequestParam(required = false) Integer mes, Pageable pageable, Authentication auth) { return service.listar(descricao, categoria, ano, mes, pageable, auth.getName()); }
    @GetMapping("/{id}") public ReceitaDTO buscar(@PathVariable String id, Authentication auth) { return service.buscarPorId(id, auth.getName()); }
    @PutMapping("/{id}") public ReceitaDTO atualizar(@PathVariable String id, @Valid @RequestBody ReceitaDTO dto, Authentication auth) { return service.atualizar(id, dto, auth.getName()); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deletar(@PathVariable String id, Authentication auth) { service.deletar(id, auth.getName()); }
}
