package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Conta;
import com.example.demo.model.ContaSimples;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public Conta abrirConta(Conta conta) {
        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id).orElse(null);
    }

    public Conta ativarConta(Long id) {
        Conta conta = contaRepository.findById(id).orElse(null);
        if (conta != null) {
            conta.setAtiva(true);
            contaRepository.save(conta);
        }
        return conta;
    }

    public Conta desativarConta(Long id) {
        Conta conta = contaRepository.findById(id).orElse(null);
        if (conta != null) {
            conta.setAtiva(false);
            contaRepository.save(conta);
        }
        return conta;
    }

    public boolean realizarDeposito(Long contaId, double valor) {
        Conta conta = contaRepository.findById(contaId).orElse(null);
        if (conta != null && conta.isAtiva()) {
            conta.setSaldo(conta.getSaldo() + valor);
            conta.getMovimentacoes().add("Depósito: " + valor);
            contaRepository.save(conta);
            return true;
        }
        return false;
    }

    public boolean realizarSaque(Long contaId, double valor) {
        Conta conta = contaRepository.findById(contaId).orElse(null);
        if (conta != null && conta.isAtiva()) {
            if (conta instanceof ContaSimples && conta.getSaldo() < valor) {
                return false;
            }
            conta.setSaldo(conta.getSaldo() - valor);
            conta.getMovimentacoes().add("Saque: " + valor);
            contaRepository.save(conta);
            return true;
        }
        return false;
    }

    public boolean realizarTransferencia(Long contaOrigemId, Long contaDestinoId, double valor) {
        Conta contaOrigem = contaRepository.findById(contaOrigemId).orElse(null);
        Conta contaDestino = contaRepository.findById(contaDestinoId).orElse(null);
        if (contaOrigem != null && contaDestino != null && contaOrigem.isAtiva() && contaDestino.isAtiva()) {
            if (realizarSaque(contaOrigemId, valor)) {
                realizarDeposito(contaDestinoId, valor);
                return true;
            }
        }
        return false;
    }

    public boolean existemContasAtivas(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            return false;
        }
        return contaRepository.existsByClienteAndAtiva(cliente, true);
    }
}