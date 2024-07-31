package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String senha;
    private double saldo;
    private boolean ativa;

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ElementCollection
    private List<String> movimentacoes;

    private double limite;
    private double taxaJuros;
}