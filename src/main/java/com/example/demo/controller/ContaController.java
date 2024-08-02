package com.example.demo.controller;

import com.example.demo.dto.ContaDTO;
import com.example.demo.model.Conta;
import com.example.demo.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestBody ContaDTO contaDTO) {
        Conta conta = contaDTO.toConta();
        Conta novaConta = contaService.criarConta(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ContaDTO(novaConta));
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        List<ContaDTO> contaDTOs = contas.stream()
                .map(ContaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarContaPorId(@PathVariable Long id) {
        Conta conta = contaService.buscarContaPorId(id);
        if (conta != null) {
            return ResponseEntity.ok(new ContaDTO(conta));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizarConta(@PathVariable Long id, @RequestBody ContaDTO contaDTO) {
        Conta conta = contaDTO.toConta();
        Conta contaAtualizada = contaService.atualizarConta(id, conta);
        if (contaAtualizada != null) {
            return ResponseEntity.ok(new ContaDTO(contaAtualizada));
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