package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;


import java.util.List;

@Entity
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    private String cpf;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    @OneToMany(mappedBy = "cliente")
    private List<Conta> contas;
}