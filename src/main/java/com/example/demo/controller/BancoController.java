package com.example.demo.controller;

import com.example.demo.model.Banco;
import com.example.demo.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @PostMapping
    public ResponseEntity<Banco> criarBanco(@RequestBody Banco banco) {
        Banco novoBanco = bancoService.criarBanco(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBanco);
    }
    @GetMapping
    public ResponseEntity<List<Banco>> listarBancos() {
        List<Banco> bancos = bancoService.listarBancos();
        return ResponseEntity.ok(bancos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Banco> buscarBancoPorId(@PathVariable Long id) {
        Banco banco = bancoService.buscarBancoPorId(id);
        if (banco != null) {
            return ResponseEntity.ok(banco);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Banco> atualizarBanco(@PathVariable Long id, @RequestBody Banco banco) {
        Banco bancoAtualizado = bancoService.atualizarBanco(id, banco);
        if (bancoAtualizado != null) {
            return ResponseEntity.ok(bancoAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerBanco(@PathVariable Long id) {
        boolean removido = bancoService.removerBanco(id);
        if (removido) {
            return ResponseEntity.ok("Banco removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Banco não encontrado.");
        }
    }
}