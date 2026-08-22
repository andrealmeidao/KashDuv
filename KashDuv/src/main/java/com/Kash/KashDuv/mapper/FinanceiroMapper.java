package com.Kash.KashDuv.mapper;

import com.Kash.KashDuv.dto.DespesaDTO;
import com.Kash.KashDuv.dto.ReceitaDTO;
import com.Kash.KashDuv.entity.Despesa;
import com.Kash.KashDuv.entity.Receita;

public final class FinanceiroMapper {
    private FinanceiroMapper() { }

    public static Despesa paraEntidade(DespesaDTO dto) {
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao()); despesa.setValor(dto.getValor());
        despesa.setCategoria(dto.getCategoria()); despesa.setData(dto.getData());
        return despesa;
    }

    public static DespesaDTO paraDto(Despesa despesa) {
        DespesaDTO dto = new DespesaDTO();
        dto.setId(despesa.getId()); dto.setDescricao(despesa.getDescricao()); dto.setValor(despesa.getValor());
        dto.setCategoria(despesa.getCategoria()); dto.setData(despesa.getData()); dto.setCriadoEm(despesa.getCriadoEm());
        return dto;
    }

    public static Receita paraEntidade(ReceitaDTO dto) {
        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao()); receita.setValor(dto.getValor());
        receita.setCategoria(dto.getCategoria()); receita.setData(dto.getData());
        return receita;
    }

    public static ReceitaDTO paraDto(Receita receita) {
        ReceitaDTO dto = new ReceitaDTO();
        dto.setId(receita.getId()); dto.setDescricao(receita.getDescricao()); dto.setValor(receita.getValor());
        dto.setCategoria(receita.getCategoria()); dto.setData(receita.getData()); dto.setCriadoEm(receita.getCriadoEm());
        return dto;
    }
}
