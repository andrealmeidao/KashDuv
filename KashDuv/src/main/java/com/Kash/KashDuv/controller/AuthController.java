package com.Kash.KashDuv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Kash.KashDuv.dto.RegistroUsuarioDTO;
import com.Kash.KashDuv.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioService service;
    public AuthController(UsuarioService service) { this.service = service; }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@Valid @RequestBody RegistroUsuarioDTO dto) {
        service.registrar(dto.username(), dto.password());
    }
}