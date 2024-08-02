package com.example.demo.service;

import com.example.demo.model.Conta;
import com.example.demo.model.Movimentacao;

import com.example.demo.repository.ContaRepository;
import com.example.demo.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public void realizarDeposito(Movimentacao deposito) {
        Conta conta = contaRepository.findById(deposito.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSaldo(conta.getSaldo() + deposito.getValor());
        contaRepository.save(conta);
        movimentacaoRepository.save(deposito);
    }

    @Transactional
    public void realizarSaque(Movimentacao saque) {
        Conta conta = contaRepository.findById(saque.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (conta.getSaldo() < saque.getValor()) {
            throw new RuntimeException("Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo() - saque.getValor());
        contaRepository.save(conta);
        movimentacaoRepository.save(saque);
    }

    @Transactional
    public void realizarTransferencia(Movimentacao transferencia) {
        Conta contaOrigem = contaRepository.findById(transferencia.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));
        Conta contaDestino = contaRepository.findById(transferencia.getContaDestino().getId())
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        if (contaOrigem.getSaldo() < transferencia.getValor()) {
            throw new RuntimeException("Saldo insuficiente para transferência");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - transferencia.getValor());
        contaDestino.setSaldo(contaDestino.getSaldo() + transferencia.getValor());

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
        movimentacaoRepository.save(transferencia);
    }
    @Transactional
    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }
}