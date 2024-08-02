package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.cadastrarCliente(cliente);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testCadastrarClienteSemId() {
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenThrow(new DataAccessResourceFailureException("Erro ao salvar cliente"));

        assertThrows(DataAccessResourceFailureException.class, () -> {
            clienteService.cadastrarCliente(cliente);
        });
    }

    @Test
    public void testListarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        clientes.add(cliente1);
        clientes.add(cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.listarClientes();

        assertEquals(clientes, result);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testListarClientesComErro() {
        when(clienteRepository.findAll()).thenThrow(new DataAccessResourceFailureException("Erro ao listar clientes"));

        assertThrows(DataAccessResourceFailureException.class, () -> {
            clienteService.listarClientes();
        });
    }

    @Test

    public void testRemoverCliente() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);
        clienteService.removerCliente(id);
        verify(clienteRepository, times(1)).deleteById(id);

    }

    @Test
    public void testRemoverClienteComErro() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.removerCliente(id);
        });
        assertEquals("Cliente não encontrado com ID " + id, exception.getMessage());
    }

}
