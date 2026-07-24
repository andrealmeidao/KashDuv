package com.Kash.KashDuv.controller;

import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<Despesa> criar(@RequestBody Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(despesa));
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
    public ResponseEntity<Despesa> atualizar(@PathVariable String id, @RequestBody Despesa despesa) {
        return ResponseEntity.ok(service.atualizar(id, despesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}