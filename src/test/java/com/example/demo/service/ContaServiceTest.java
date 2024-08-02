package com.example.demo.service;

import com.example.demo.model.Conta;
import com.example.demo.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarConta() {
        Conta conta = new Conta();
        conta.setId(1L);
        when(contaRepository.save(conta)).thenReturn(conta);

        Conta result = contaService.criarConta(conta);

        assertEquals(conta, result);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    public void testCriarContaSemId() {
        Conta conta = new Conta();
        when(contaRepository.save(conta)).thenThrow(new DataAccessResourceFailureException("Erro ao salvar conta"));

        assertThrows(DataAccessResourceFailureException.class, () -> {
            contaService.criarConta(conta);
        });
    }

    @Test
    public void testListarContas() {
        List<Conta> contas = new ArrayList<>();
        Conta conta1 = new Conta();
        Conta conta2 = new Conta();
        contas.add(conta1);
        contas.add(conta2);

        when(contaRepository.findAll()).thenReturn(contas);

        List<Conta> result = contaService.listarContas();

        assertEquals(contas, result);
        verify(contaRepository, times(1)).findAll();
    }

    @Test
    public void testListarContasComErro() {
        when(contaRepository.findAll()).thenThrow(new DataAccessResourceFailureException("Erro ao listar contas"));

        assertThrows(DataAccessResourceFailureException.class, () -> {
            contaService.listarContas();
        });
    }

    @Test
    public void testBuscarContaPorId() {
        Long id = 1L;
        Conta conta = new Conta();
        when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

        Conta result = contaService.buscarContaPorId(id);

        assertEquals(conta, result);
        verify(contaRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarContaPorIdVazio() {
        Long id = 1L;
        when(contaRepository.findById(id)).thenReturn(Optional.empty());

        Conta result = contaService.buscarContaPorId(id);

        assertNull(result);
        verify(contaRepository, times(1)).findById(id);
    }

    @Test
    public void testAtualizarConta() {
        Long id = 1L;
        Conta conta = new Conta();
        conta.setId(id);
        when(contaRepository.existsById(id)).thenReturn(true);
        when(contaRepository.save(conta)).thenReturn(conta);

        Conta result = contaService.atualizarConta(id, conta);

        assertEquals(conta, result);
        verify(contaRepository, times(1)).existsById(id);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    public void testAtualizarContaVazia() {
        Long id = 1L;
        Conta conta = new Conta();
        when(contaRepository.existsById(id)).thenReturn(false);

        Conta result = contaService.atualizarConta(id, conta);

        assertNull(result);
        verify(contaRepository, times(1)).existsById(id);
        verify(contaRepository, never()).save(conta);
    }

    @Test
    public void testRemoverConta() {
        Long id = 1L;
        when(contaRepository.existsById(id)).thenReturn(true);
        doNothing().when(contaRepository).deleteById(id);

        boolean result = contaService.removerConta(id);

        assertTrue(result);
        verify(contaRepository, times(1)).existsById(id);
        verify(contaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testRemoverContaInexistente() {
        Long id = 1L;
        when(contaRepository.existsById(id)).thenReturn(false);

        boolean result = contaService.removerConta(id);

        assertFalse(result);
        verify(contaRepository, times(1)).existsById(id);
        verify(contaRepository, never()).deleteById(id);
    }

    @Test
    public void testRemoverContaComErro() {
        Long id = 1L;
        when(contaRepository.existsById(id)).thenReturn(true);
        doThrow(new DataAccessResourceFailureException("Erro ao remover conta")).when(contaRepository).deleteById(id);

        assertThrows(DataAccessResourceFailureException.class, () -> {
            contaService.removerConta(id);
        });
    }
}
