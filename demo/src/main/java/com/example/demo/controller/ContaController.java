package com.example.demo.controller;

import com.example.demo.model.Conta;
import com.example.demo.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController

{
    @Autowired
    private ContaService contaService;

    @PostMapping
    public Conta abrirConta(@RequestBody Conta conta) {
        return contaService.abrirConta(conta);
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public Conta buscarContaPorId(@PathVariable Long id) {
        return contaService.buscarContaPorId(id);
    }
}
