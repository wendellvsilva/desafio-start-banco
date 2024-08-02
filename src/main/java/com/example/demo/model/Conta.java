package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Getter
public class Conta {


    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String senha;
    private double saldo;
    private boolean ativa;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Movimentacao> movimentacoes = new ArrayList<>();


}