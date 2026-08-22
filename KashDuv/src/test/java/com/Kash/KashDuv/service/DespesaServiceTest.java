package com.Kash.KashDuv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.Kash.KashDuv.dto.DespesaDTO;
import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.exception.RecursoNaoEncontradoException;
import com.Kash.KashDuv.repository.DespesaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DespesaServiceTest {
    private final DespesaRepository repository = Mockito.mock(DespesaRepository.class);
    private final UsuarioService usuarioService = Mockito.mock(UsuarioService.class);
    private final DespesaService service = new DespesaService(repository, usuarioService);

    @Test
    void atualizarRegistroInexistenteLancaExcecaoDeDominio() {
        when(repository.findByIdAndUsuarioUsername("ausente", "ana")).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar("ausente", dto(), "ana"));
    }

    @Test
    void atualizarAlteraCamposDaDespesaDoUsuario() {
        Despesa existente = new Despesa(); existente.setId("1"); existente.setCriadoEm(LocalDate.now());
        when(repository.findByIdAndUsuarioUsername("1", "ana")).thenReturn(Optional.of(existente));
        when(repository.save(any(Despesa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        DespesaDTO resultado = service.atualizar("1", dto(), "ana");
        assertEquals("Mercado", resultado.getDescricao());
        assertEquals(new BigDecimal("25.50"), resultado.getValor());
    }

    private DespesaDTO dto() {
        DespesaDTO dto = new DespesaDTO(); dto.setDescricao("Mercado"); dto.setValor(new BigDecimal("25.50"));
        dto.setCategoria("Alimentação"); dto.setData(LocalDate.now()); return dto;
    }
}
