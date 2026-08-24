package com.Kash.KashDuv.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.Kash.KashDuv.repository.DespesaRepository;
import com.Kash.KashDuv.repository.ReceitaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DespesaControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private DespesaRepository despesas;
    @Autowired private ReceitaRepository receitas;

    @BeforeEach
    void limparDados() { receitas.deleteAll(); despesas.deleteAll(); }

    @Test
    void criaDespesaComDtoAutenticado() throws Exception {
        mockMvc.perform(post("/api/despesas").with(httpBasic("admin", "test-password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Mercado\",\"valor\":45.90,\"categoria\":\"Alimentação\",\"data\":\"2026-08-22\"}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.descricao").value("Mercado"));
    }

    @Test
    void retornaErroEstruturadoParaValidacao() throws Exception {
        mockMvc.perform(post("/api/despesas").with(httpBasic("admin", "test-password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"\",\"valor\":0,\"categoria\":\"\",\"data\":\"2099-01-01\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").isNotEmpty())
                .andExpect(jsonPath("$.campo").isNotEmpty()).andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void atualizarEExcluirInexistentesRetorna404() throws Exception {
        String corpo = "{\"descricao\":\"Mercado\",\"valor\":45.90,\"categoria\":\"Alimentação\",\"data\":\"2026-08-22\"}";
        mockMvc.perform(put("/api/despesas/id-inexistente").with(httpBasic("admin", "test-password")).contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.mensagem").value("Despesa não encontrada"));
        mockMvc.perform(delete("/api/despesas/id-inexistente").with(httpBasic("admin", "test-password")))
                .andExpect(status().isNotFound());
    }

    @Test
    void chamadaSemAutenticacaoRetorna401() throws Exception {
        mockMvc.perform(get("/api/despesas")).andExpect(status().isUnauthorized());
    }

    @Test
    void anoOuMesInvalidoRetorna400() throws Exception {
        mockMvc.perform(get("/api/resumo/2026/13").with(httpBasic("admin", "test-password")))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.mensagem").value("Parâmetro inválido"));
    }
}
