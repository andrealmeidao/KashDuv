package com.Kash.KashDuv.service;

import com.Kash.KashDuv.dto.DespesaDTO;
import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.exception.RecursoNaoEncontradoException;
import com.Kash.KashDuv.mapper.FinanceiroMapper;
import com.Kash.KashDuv.repository.DespesaRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DespesaService {
    private final DespesaRepository repository;
    private final UsuarioService usuarioService;
    public DespesaService(DespesaRepository repository, UsuarioService usuarioService) { this.repository = repository; this.usuarioService = usuarioService; }
    public DespesaDTO criar(DespesaDTO dto, String usuario) { Despesa d = FinanceiroMapper.paraEntidade(dto); d.setUsuario(usuarioService.buscar(usuario)); return FinanceiroMapper.paraDto(repository.save(d)); }
    public Page<DespesaDTO> listar(String descricao, String categoria, Integer ano, Integer mes, Pageable pageable, String usuario) { LocalDate[] p = periodo(ano, mes); return repository.buscar(usuario, descricao, categoria, p[0], p[1], pageable).map(FinanceiroMapper::paraDto); }
    public DespesaDTO buscarPorId(String id, String usuario) { return FinanceiroMapper.paraDto(encontrar(id, usuario)); }
    public DespesaDTO atualizar(String id, DespesaDTO dto, String usuario) { Despesa d = encontrar(id, usuario); d.setDescricao(dto.getDescricao()); d.setValor(dto.getValor()); d.setCategoria(dto.getCategoria()); d.setData(dto.getData()); return FinanceiroMapper.paraDto(repository.save(d)); }
    public void deletar(String id, String usuario) { repository.delete(encontrar(id, usuario)); }
    public List<Despesa> listarNoPeriodo(String usuario, LocalDate inicio, LocalDate fim) { return repository.findByUsuarioUsernameAndDataBetween(usuario, inicio, fim); }
    private Despesa encontrar(String id, String usuario) { return repository.findByIdAndUsuarioUsername(id, usuario).orElseThrow(() -> new RecursoNaoEncontradoException("Despesa não encontrada")); }
    private LocalDate[] periodo(Integer ano, Integer mes) { if (ano == null || mes == null) return new LocalDate[] {null, null}; YearMonth ym = YearMonth.of(ano, mes); return new LocalDate[] {ym.atDay(1), ym.atEndOfMonth()}; }
}
