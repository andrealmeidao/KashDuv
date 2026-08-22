package com.Kash.KashDuv.service;

import com.Kash.KashDuv.entity.Usuario;
import com.Kash.KashDuv.exception.RecursoNaoEncontradoException;
import com.Kash.KashDuv.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }
    public Usuario buscar(String username) { return repository.findByUsername(username).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado")); }
}
