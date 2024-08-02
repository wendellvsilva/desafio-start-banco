package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String senha;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conta> contas;
}