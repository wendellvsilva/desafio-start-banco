package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ContaService contaService;

    @PostMapping Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return clienteService.cadastrarCliente(cliente);
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerCliente(@PathVariable Long id) {
        if (contaService.existemContasAtivas(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente possui contas ativas.");
        }
        clienteService.removerCliente(id);
        return ResponseEntity.ok("Cliente removido com sucesso.");
    }
}