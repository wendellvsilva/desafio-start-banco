package com.example.demo.controller;

import com.example.demo.model.Banco;
import com.example.demo.model.Conta;
import com.example.demo.model.Movimentacao;
import com.example.demo.service.MovimentacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimentacaoController.class)
@Import(TestSecurityConfig.class)
public class MovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovimentacaoService movimentacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRealizarDeposito() throws Exception {
        Movimentacao deposito = new Movimentacao();
        deposito.setId(1L);
        deposito.setOperacao("DEPOSITO");
        deposito.setValor(500.00);
        deposito.setData(LocalDateTime.now());
        deposito.setConta(new Conta());
        deposito.setBanco(new Banco());

        doNothing().when(movimentacaoService).realizarDeposito(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/deposito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposito)))
                .andExpect(status().isOk())
                .andExpect(content().string("Depósito realizado com sucesso"));
    }

    @Test
    public void testRealizarSaque() throws Exception {
        Movimentacao saque = new Movimentacao();
        saque.setId(2L);
        saque.setOperacao("SAQUE");
        saque.setValor(200.00);
        saque.setData(LocalDateTime.now());
        saque.setConta(new Conta());
        saque.setBanco(new Banco());

        doNothing().when(movimentacaoService).realizarSaque(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/saque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saque)))
                .andExpect(status().isOk())
                .andExpect(content().string("Saque realizado com sucesso"));
    }

    @Test
    public void testRealizarTransferencia() throws Exception {
        Movimentacao transferencia = new Movimentacao();
        transferencia.setId(3L);
        transferencia.setOperacao("TRANSFERENCIA");
        transferencia.setValor(300.00);
        transferencia.setData(LocalDateTime.now());
        transferencia.setConta(new Conta());
        transferencia.setContaDestino(new Conta());
        transferencia.setBanco(new Banco());

        doNothing().when(movimentacaoService).realizarTransferencia(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferencia)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferência realizada com sucesso"));
    }

    @Test
    public void testRealizarDeposito_Falha() throws Exception {
        Movimentacao deposito = new Movimentacao();
        deposito.setOperacao("DEPOSITO");
        deposito.setValor(500.00);
        deposito.setData(LocalDateTime.now());
        deposito.setConta(new Conta());
        deposito.setBanco(new Banco());

        doThrow(new RuntimeException("Erro ao realizar depósito")).when(movimentacaoService).realizarDeposito(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/deposito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposito)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro ao realizar depósito"));
    }

    @Test
    public void testRealizarSaque_Falha() throws Exception {
        Movimentacao saque = new Movimentacao();
        saque.setOperacao("SAQUE");
        saque.setValor(200.00);
        saque.setData(LocalDateTime.now());
        saque.setConta(new Conta());
        saque.setBanco(new Banco());

        doThrow(new RuntimeException("Saldo insuficiente")).when(movimentacaoService).realizarSaque(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/saque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saque)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Saldo insuficiente"));
    }

    @Test
    public void testRealizarTransferencia_Falha() throws Exception {
        Movimentacao transferencia = new Movimentacao();
        transferencia.setOperacao("TRANSFERENCIA");
        transferencia.setValor(300.00);
        transferencia.setData(LocalDateTime.now());
        transferencia.setConta(new Conta());
        transferencia.setContaDestino(new Conta());
        transferencia.setBanco(new Banco());

        doThrow(new RuntimeException("Erro na transferência")).when(movimentacaoService).realizarTransferencia(any(Movimentacao.class));

        mockMvc.perform(post("/movimentacoes/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferencia)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro na transferência"));
    }
}
