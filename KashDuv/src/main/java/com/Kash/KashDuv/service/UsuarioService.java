package com.Kash.KashDuv.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kash.KashDuv.entity.Usuario;
import com.Kash.KashDuv.exception.RecursoNaoEncontradoException;
import com.Kash.KashDuv.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) { this.repository = repository; this.passwordEncoder = passwordEncoder; }
    public Usuario buscar(String username) { return repository.findByUsername(username).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado")); }
    @Transactional
    public void registrar(String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username.trim());
        usuario.setPassword(passwordEncoder.encode(password));
        repository.save(usuario);
    }
}
