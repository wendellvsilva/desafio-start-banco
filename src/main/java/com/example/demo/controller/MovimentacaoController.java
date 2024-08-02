package com.example.demo.controller;

import com.example.demo.dto.MovimentacaoDTO;
import com.example.demo.model.Banco;
import com.example.demo.model.Conta;
import com.example.demo.model.Movimentacao;
import com.example.demo.service.BancoService;
import com.example.demo.service.ContaService;
import com.example.demo.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private BancoService bancoService;

    @PostMapping("/deposito")
    public ResponseEntity<String> realizarDeposito(@RequestBody MovimentacaoDTO depositoDTO) {
        try {
            Conta conta = contaService.buscarContaPorId(depositoDTO.getContaId());
            Banco banco = bancoService.buscarBancoPorId(depositoDTO.getBancoId());
            Movimentacao deposito = depositoDTO.toMovimentacao(conta, banco, null);
            movimentacaoService.realizarDeposito(deposito);
            return new ResponseEntity<>("Depósito realizado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saque")
    public ResponseEntity<String> realizarSaque(@RequestBody MovimentacaoDTO saqueDTO) {
        try {
            Conta conta = contaService.buscarContaPorId(saqueDTO.getContaId());
            Banco banco = bancoService.buscarBancoPorId(saqueDTO.getBancoId());
            Movimentacao saque = saqueDTO.toMovimentacao(conta, banco, null);
            movimentacaoService.realizarSaque(saque);
            return new ResponseEntity<>("Saque realizado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(@RequestBody MovimentacaoDTO transferenciaDTO) {
        try {
            Conta contaOrigem = contaService.buscarContaPorId(transferenciaDTO.getContaId());
            Conta contaDestino = contaService.buscarContaPorId(transferenciaDTO.getContaDestinoId());
            Banco banco = bancoService.buscarBancoPorId(transferenciaDTO.getBancoId());
            Movimentacao transferencia = transferenciaDTO.toMovimentacao(contaOrigem, banco, contaDestino);
            movimentacaoService.realizarTransferencia(transferencia);
            return new ResponseEntity<>("Transferência realizada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoDTO>> listarMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        List<MovimentacaoDTO> movimentacaoDTOs = movimentacoes.stream().map(MovimentacaoDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(movimentacaoDTOs, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<MovimentacaoDTO>> listarMovimentacoesComId(@PathVariable Long id) {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        List<MovimentacaoDTO> movimentacaoDTOs = movimentacoes.stream().map(MovimentacaoDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(movimentacaoDTOs, HttpStatus.OK);
    }
}
