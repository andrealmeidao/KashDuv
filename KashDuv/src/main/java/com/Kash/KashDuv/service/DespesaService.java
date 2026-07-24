package com.Kash.KashDuv.service;

import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository repository;

    public Despesa criar(Despesa despesa) {
        return repository.save(despesa);
    }

    public List<Despesa> listar() {
        return repository.findAll();
    }

    public Optional<Despesa> buscarPorId(String id) {
        return repository.findById(id);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }

    public Despesa atualizar(String id, Despesa despesaAtualizada) {
        return repository.findById(id).map(despesa -> {
            despesa.setDescricao(despesaAtualizada.getDescricao());
            despesa.setValor(despesaAtualizada.getValor());
            despesa.setCategoria(despesaAtualizada.getCategoria());
            despesa.setData(despesaAtualizada.getData());
            return repository.save(despesa);
        }).orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }
}