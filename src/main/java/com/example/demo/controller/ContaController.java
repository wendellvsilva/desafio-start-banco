package com.example.demo.controller;

import com.example.demo.model.Conta;
import com.example.demo.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.criarConta(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null) {
            return ResponseEntity.ok(conta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
        Conta contaAtualizada = contaService.atualizarConta(id, conta);
        if (contaAtualizada != null) {
            return ResponseEntity.ok(contaAtualizada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerConta(@PathVariable Long id) {
        boolean removido = contaService.removerConta(id);
        if (removido) {
            return ResponseEntity.ok("Conta removida com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
    }
}