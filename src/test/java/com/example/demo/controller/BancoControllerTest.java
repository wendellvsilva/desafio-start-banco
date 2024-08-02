package com.example.demo.controller;

import com.example.demo.model.Banco;
import com.example.demo.service.BancoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@Import(TestSecurityConfig.class)
@WebMvcTest(BancoController.class)
public class BancoControllerTest {

    @MockBean

    private BancoService bancoService;


    @Autowired

    private MockMvc mockMvc;


    @Autowired

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarBanco() throws Exception {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setNome("Banco Exemplo");

        when(bancoService.criarBanco(any(Banco.class))).thenReturn(banco);

        mockMvc.perform(post("/bancos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(banco)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Banco Exemplo"));
    }

    @Test
    public void testListarBancos() throws Exception {
        Banco banco1 = new Banco();
        banco1.setId(1L);
        banco1.setNome("Banco A");

        Banco banco2 = new Banco();
        banco2.setId(2L);
        banco2.setNome("Banco B");

        when(bancoService.listarBancos()).thenReturn(List.of(banco1, banco2));

        mockMvc.perform(get("/bancos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Banco A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Banco B"));
    }

    @Test
    public void testBuscarBancoPorId() throws Exception {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setNome("Banco Exemplo");

        when(bancoService.buscarBancoPorId(1L)).thenReturn(banco);

        mockMvc.perform(get("/bancos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Banco Exemplo"));
    }

    @Test
    public void testBuscarBancoPorId_NaoEncontrado() throws Exception {
        when(bancoService.buscarBancoPorId(1L)).thenReturn(null);

        mockMvc.perform(get("/bancos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarBanco() throws Exception {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setNome("Banco Atualizado");

        when(bancoService.atualizarBanco(eq(1L), any(Banco.class))).thenReturn(banco);

        mockMvc.perform(put("/bancos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(banco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Banco Atualizado"));
    }

    @Test
    public void testAtualizarBanco_NaoEncontrado() throws Exception {
        Banco banco = new Banco();
        banco.setId(1L);
        banco.setNome("Banco Atualizado");

        when(bancoService.atualizarBanco(eq(1L), any(Banco.class))).thenReturn(null);

        mockMvc.perform(put("/bancos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(banco)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoverBanco() throws Exception {
        when(bancoService.removerBanco(1L)).thenReturn(true);

        mockMvc.perform(delete("/bancos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Banco removido com sucesso."));
    }

    @Test
    public void testRemoverBanco_NaoEncontrado() throws Exception {
        when(bancoService.removerBanco(1L)).thenReturn(false);

        mockMvc.perform(delete("/bancos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Banco não encontrado."));
    }
}
