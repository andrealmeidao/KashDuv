package com.Kash.KashDuv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import com.Kash.KashDuv.dto.ReceitaDTO;
import com.Kash.KashDuv.entity.Receita;
import com.Kash.KashDuv.exception.RecursoNaoEncontradoException;
import com.Kash.KashDuv.repository.ReceitaRepository;

class ReceitaServiceTest {
    private final ReceitaRepository repository = Mockito.mock(ReceitaRepository.class);
    private final UsuarioService usuarioService = Mockito.mock(UsuarioService.class);
    private final ReceitaService service = new ReceitaService(repository, usuarioService);

    @Test
    void criarEAtualizarReceita() {
        Receita entidade = new Receita(); entidade.setDescricao("Salário"); entidade.setValor(new BigDecimal("100"));
        when(usuarioService.buscar("ana")).thenReturn(new com.Kash.KashDuv.entity.Usuario());
        when(repository.save(any(Receita.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertEquals("Salário", service.criar(dto(), "ana").getDescricao());
        when(repository.findByIdAndUsuarioUsername("1", "ana")).thenReturn(Optional.of(entidade));
        assertEquals(new BigDecimal("100"), service.atualizar("1", dto(), "ana").getValor());
    }

    @Test
    void listarRetornaPaginaDoUsuario() {
        when(repository.buscar(any(), any(), any(), any(), any(), any())).thenReturn(new org.springframework.data.domain.PageImpl<>(java.util.List.of(new Receita())));
        assertEquals(1, service.listar(null, null, null, null, org.springframework.data.domain.Pageable.unpaged(), "ana").getTotalElements());
    }

    @Test
    void atualizarAusenteRetorna404() {
        when(repository.findByIdAndUsuarioUsername("ausente", "ana")).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar("ausente", dto(), "ana"));
    }

    private ReceitaDTO dto() {
        ReceitaDTO dto = new ReceitaDTO(); dto.setDescricao("Salário"); dto.setValor(new BigDecimal("100"));
        dto.setCategoria("Salário"); dto.setData(LocalDate.now()); return dto;
    }
}