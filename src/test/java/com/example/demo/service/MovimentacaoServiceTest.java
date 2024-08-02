package com.example.demo.service;

import com.example.demo.model.Conta;
import com.example.demo.model.Movimentacao;
import com.example.demo.repository.ContaRepository;
import com.example.demo.repository.MovimentacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovimentacaoServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRealizarDeposito() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(100.0);

        Movimentacao deposito = new Movimentacao();
        deposito.setConta(conta);
        deposito.setValor(50.0);

        when(contaRepository.findById(conta.getId())).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(conta);
        when(movimentacaoRepository.save(deposito)).thenReturn(deposito);

        movimentacaoService.realizarDeposito(deposito);

        assertEquals(150.0, conta.getSaldo());
        verify(contaRepository, times(1)).save(conta);
        verify(movimentacaoRepository, times(1)).save(deposito);
    }

    @Test
    public void testRealizarDepositoContaNaoEncontrada() {
        Movimentacao deposito = new Movimentacao();
        deposito.setConta(new Conta());
        deposito.getConta().setId(1L);

        when(contaRepository.findById(deposito.getConta().getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarDeposito(deposito);
        });
        assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    public void testRealizarSaque() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(100.0);

        Movimentacao saque = new Movimentacao();
        saque.setConta(conta);
        saque.setValor(50.0);

        when(contaRepository.findById(conta.getId())).thenReturn(Optional.of(conta));
        when(contaRepository.save(conta)).thenReturn(conta);
        when(movimentacaoRepository.save(saque)).thenReturn(saque);

        movimentacaoService.realizarSaque(saque);

        assertEquals(50.0, conta.getSaldo());
        verify(contaRepository, times(1)).save(conta);
        verify(movimentacaoRepository, times(1)).save(saque);
    }

    @Test
    public void testRealizarSaqueSemSaldo() {
        Conta conta = new Conta();
        conta.setId(1L);
        conta.setSaldo(30.0);

        Movimentacao saque = new Movimentacao();
        saque.setConta(conta);
        saque.setValor(50.0);

        when(contaRepository.findById(conta.getId())).thenReturn(Optional.of(conta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarSaque(saque);
        });
        assertEquals("Saldo insuficiente", exception.getMessage());
    }

    @Test
    public void testRealizarSaqueSemConta() {
        Movimentacao saque = new Movimentacao();
        saque.setConta(new Conta());
        saque.getConta().setId(1L);

        when(contaRepository.findById(saque.getConta().getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarSaque(saque);
        });
        assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    public void testRealizarTransferencia() {
        Conta contaOrigem = new Conta();
        contaOrigem.setId(1L);
        contaOrigem.setSaldo(100.0);

        Conta contaDestino = new Conta();
        contaDestino.setId(2L);
        contaDestino.setSaldo(50.0);

        Movimentacao transferencia = new Movimentacao();
        transferencia.setConta(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setValor(30.0);

        when(contaRepository.findById(contaOrigem.getId())).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(contaDestino.getId())).thenReturn(Optional.of(contaDestino));
        when(contaRepository.save(contaOrigem)).thenReturn(contaOrigem);
        when(contaRepository.save(contaDestino)).thenReturn(contaDestino);
        when(movimentacaoRepository.save(transferencia)).thenReturn(transferencia);

        movimentacaoService.realizarTransferencia(transferencia);

        assertEquals(70.0, contaOrigem.getSaldo());
        assertEquals(80.0, contaDestino.getSaldo());
        verify(contaRepository, times(1)).save(contaOrigem);
        verify(contaRepository, times(1)).save(contaDestino);
        verify(movimentacaoRepository, times(1)).save(transferencia);
    }

    @Test
    public void testRealizarTransferenciaComSaldoInsuficiente() {
        Conta contaOrigem = new Conta();
        contaOrigem.setId(1L);
        contaOrigem.setSaldo(30.0);

        Conta contaDestino = new Conta();
        contaDestino.setId(2L);

        Movimentacao transferencia = new Movimentacao();
        transferencia.setConta(contaOrigem);
        transferencia.setContaDestino(contaDestino);
        transferencia.setValor(50.0);

        when(contaRepository.findById(contaOrigem.getId())).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(contaDestino.getId())).thenReturn(Optional.of(contaDestino));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarTransferencia(transferencia);
        });
        assertEquals("Saldo insuficiente para transferência", exception.getMessage());
    }

    @Test
    public void testRealizarTransferenciaComContaDestinoNaoEncontrada() {
        Conta contaOrigem = new Conta();
        contaOrigem.setId(1L);
        contaOrigem.setSaldo(100.0);

        Movimentacao transferencia = new Movimentacao();
        transferencia.setConta(contaOrigem);
        transferencia.setContaDestino(new Conta());
        transferencia.getContaDestino().setId(2L);
        transferencia.setValor(50.0);

        when(contaRepository.findById(contaOrigem.getId())).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(transferencia.getContaDestino().getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarTransferencia(transferencia);
        });
        assertEquals("Conta de destino não encontrada", exception.getMessage());
    }

    @Test
    public void testRealizarTransferenciaComContaOrigemNaoEncontrada() {
        Conta contaDestino = new Conta();
        contaDestino.setId(2L);
        contaDestino.setSaldo(100.0);

        Movimentacao transferencia = new Movimentacao();
        transferencia.setConta(new Conta());
        transferencia.getConta().setId(1L);
        transferencia.setContaDestino(contaDestino);
        transferencia.setValor(50.0);

        when(contaRepository.findById(transferencia.getConta().getId())).thenReturn(Optional.empty());
        when(contaRepository.findById(contaDestino.getId())).thenReturn(Optional.of(contaDestino));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimentacaoService.realizarTransferencia(transferencia);
        });
        assertEquals("Conta de origem não encontrada", exception.getMessage());
    }
}
