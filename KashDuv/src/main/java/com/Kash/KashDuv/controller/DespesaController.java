package com.Kash.KashDuv.controller;

import com.Kash.KashDuv.dto.DespesaDTO;
import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<Despesa> criar(@Valid @RequestBody DespesaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(toEntity(dto)));
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable String id, @Valid @RequestBody DespesaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Despesa toEntity(DespesaDTO dto) {
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setCategoria(dto.getCategoria());
        despesa.setData(dto.getData());
        return despesa;
    }
}