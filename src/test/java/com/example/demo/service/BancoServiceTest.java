package com.example.demo.service;

import com.example.demo.model.Banco;
import com.example.demo.repository.BancoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private BancoService bancoService;

    private Banco banco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        banco = new Banco();
        banco.setId(1L);
        banco.setNome("Banco Teste");
    }

    @Test
    void testCriarBanco() {
        when(bancoRepository.save(any(Banco.class))).thenReturn(banco);

        Banco result = bancoService.criarBanco(banco);

        assertNotNull(result);
        assertEquals("Banco Teste", result.getNome());
        verify(bancoRepository, times(1)).save(banco);
    }

    @Test
    void testListarBancos() {
        when(bancoRepository.findAll()).thenReturn(Arrays.asList(banco));

        List<Banco> result = bancoService.listarBancos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(bancoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarBancoPorId() {
        when(bancoRepository.findById(1L)).thenReturn(Optional.of(banco));

        Banco result = bancoService.buscarBancoPorId(1L);

        assertNotNull(result);
        assertEquals("Banco Teste", result.getNome());
        verify(bancoRepository, times(1)).findById(1L);
    }

    @Test
    void testAtualizarBanco() {
        when(bancoRepository.existsById(1L)).thenReturn(true);
        when(bancoRepository.save(any(Banco.class))).thenReturn(banco);

        Banco result = bancoService.atualizarBanco(1L, banco);

        assertNotNull(result);
        assertEquals("Banco Teste", result.getNome());
        verify(bancoRepository, times(1)).existsById(1L);
        verify(bancoRepository, times(1)).save(banco);
    }

    @Test
    void testRemoverBanco() {
        when(bancoRepository.existsById(1L)).thenReturn(true);

        boolean result = bancoService.removerBanco(1L);

        assertTrue(result);
        verify(bancoRepository, times(1)).existsById(1L);
        verify(bancoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoverBancoInexistente() {
        when(bancoRepository.existsById(1L)).thenReturn(false);

        boolean result = bancoService.removerBanco(1L);

        assertFalse(result);
        verify(bancoRepository, times(1)).existsById(1L);
        verify(bancoRepository, times(0)).deleteById(1L);
    }
}
