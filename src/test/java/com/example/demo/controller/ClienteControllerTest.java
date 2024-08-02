package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@Import(TestSecurityConfig.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Exemplo");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cliente Exemplo"));
    }

    @Test
    public void testListarClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente A");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente B");

        List<Cliente> clientes = List.of(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Cliente A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Cliente B"));
    }

    @Test
    public void testBuscarClientePorId() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Exemplo");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Cliente Exemplo"));
    }

    @Test
    public void testBuscarClientePorId_NaoEncontrado() throws Exception {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoverCliente() throws Exception {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testRemoverCliente_NaoEncontrado() throws Exception {
        when(clienteRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNotFound());
    }
}
