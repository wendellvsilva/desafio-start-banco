package com.example.demo.controller;

import com.example.demo.model.Conta;
import com.example.demo.service.ContaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContaController.class)
@Import(TestSecurityConfig.class)
public class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContaService contaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarConta() throws Exception {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123456");
        conta.setSenha("senha123");
        conta.setSaldo(1000.00);
        conta.setAtiva(true);

        when(contaService.criarConta(any(Conta.class))).thenReturn(conta);

        mockMvc.perform(post("/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numero").value("123456"))
                .andExpect(jsonPath("$.senha").value("senha123"))
                .andExpect(jsonPath("$.saldo").value(1000.00))
                .andExpect(jsonPath("$.ativa").value(true));
    }

    @Test
    public void testListarContas() throws Exception {
        Conta conta1 = new Conta();
        conta1.setId(1L);
        conta1.setNumero("123456");
        conta1.setSenha("senha123");
        conta1.setSaldo(1000.00);
        conta1.setAtiva(true);

        Conta conta2 = new Conta();
        conta2.setId(2L);
        conta2.setNumero("654321");
        conta2.setSenha("senha456");
        conta2.setSaldo(2000.00);
        conta2.setAtiva(false);

        List<Conta> contas = List.of(conta1, conta2);

        when(contaService.listarContas()).thenReturn(contas);

        mockMvc.perform(get("/contas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].numero").value("123456"))
                .andExpect(jsonPath("$[0].senha").value("senha123"))
                .andExpect(jsonPath("$[0].saldo").value(1000.00))
                .andExpect(jsonPath("$[0].ativa").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].numero").value("654321"))
                .andExpect(jsonPath("$[1].senha").value("senha456"))
                .andExpect(jsonPath("$[1].saldo").value(2000.00))
                .andExpect(jsonPath("$[1].ativa").value(false));
    }

    @Test
    public void testBuscarContaPorId() throws Exception {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123456");
        conta.setSenha("senha123");
        conta.setSaldo(1000.00);
        conta.setAtiva(true);

        when(contaService.buscarContaPorId(anyLong())).thenReturn(conta);

        mockMvc.perform(get("/contas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numero").value("123456"))
                .andExpect(jsonPath("$.senha").value("senha123"))
                .andExpect(jsonPath("$.saldo").value(1000.00))
                .andExpect(jsonPath("$.ativa").value(true));
    }

    @Test
    public void testBuscarContaPorId_NaoEncontrado() throws Exception {
        when(contaService.buscarContaPorId(anyLong())).thenReturn(null);

        mockMvc.perform(get("/contas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarConta() throws Exception {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123456");
        conta.setSenha("novaSenha123");
        conta.setSaldo(1500.00);
        conta.setAtiva(true);

        when(contaService.atualizarConta(anyLong(), any(Conta.class))).thenReturn(conta);

        mockMvc.perform(put("/contas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numero").value("123456"))
                .andExpect(jsonPath("$.senha").value("novaSenha123"))
                .andExpect(jsonPath("$.saldo").value(1500.00))
                .andExpect(jsonPath("$.ativa").value(true));
    }

    @Test
    public void testAtualizarConta_NaoEncontrado() throws Exception {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setNumero("123456");
        conta.setSenha("novaSenha123");
        conta.setSaldo(1500.00);
        conta.setAtiva(true);

        when(contaService.atualizarConta(anyLong(), any(Conta.class))).thenReturn(null);

        mockMvc.perform(put("/contas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conta)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoverConta() throws Exception {
        when(contaService.removerConta(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/contas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Conta removida com sucesso."));
    }

    @Test
    public void testRemoverConta_NaoEncontrado() throws Exception {
        when(contaService.removerConta(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/contas/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Conta não encontrada."));
    }
}
