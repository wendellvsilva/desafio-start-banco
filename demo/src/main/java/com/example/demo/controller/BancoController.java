package com.example.demo.controller;

import com.example.demo.model.Banco;
import com.example.demo.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bancos")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @PostMapping
    public Banco criarBanco(@RequestBody Banco banco) {
        return bancoService.criarBanco(banco);
    }

    @GetMapping
    public List<Banco> listarBancos() {
        return bancoService.listarBancos();
    }

    @GetMapping("/{id}")
    public Banco buscarBancoPorId(@PathVariable Long id) {
        return bancoService.buscarBancoPorId(id);
    }

    @PostMapping("/{id}/acesso")
    public boolean acessarBanco(@PathVariable Long id, @RequestParam String senha) {
        Banco banco = bancoService.buscarBancoPorId(id);
        return banco != null && banco.getSenha().equals(senha);
    }
}