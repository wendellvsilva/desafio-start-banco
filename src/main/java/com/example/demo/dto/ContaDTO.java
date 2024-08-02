package com.example.demo.dto;

import com.example.demo.model.Conta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDTO {
    private Long id;
    private String numero;
    private String senha;
    private double saldo;
    private boolean ativa;

    public ContaDTO() {}

    public ContaDTO(Conta conta) {
        this.id = conta.getId();
        this.numero = conta.getNumero();
        this.senha = conta.getSenha();
        this.saldo = conta.getSaldo();
        this.ativa = conta.isAtiva();
    }

    public Conta toConta() {
        Conta conta = new Conta();
        conta.setId(this.id);
        conta.setNumero(this.numero);
        conta.setSenha(this.senha);
        conta.setSaldo(this.saldo);
        conta.setAtiva(this.ativa);
        return conta;
    }
}