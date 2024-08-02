package com.example.demo.controller;

import com.example.demo.model.Movimentacao;
import com.example.demo.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movimentacoes")

public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping("/deposito")

    public ResponseEntity<String> realizarDeposito(@RequestBody Movimentacao deposito) {
        try {
            movimentacaoService.realizarDeposito(deposito);
            return new ResponseEntity<>("Depósito realizado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saque")
    public ResponseEntity<String> realizarSaque(@RequestBody Movimentacao saque) {
        try {
            movimentacaoService.realizarSaque(saque);
            return new ResponseEntity<>("Saque realizado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(@RequestBody Movimentacao transferencia) {
        try {
            movimentacaoService.realizarTransferencia(transferencia);
            return new ResponseEntity<>("Transferência realizada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<List<Movimentacao>> listarMovimentacoesComId() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
    }
}

