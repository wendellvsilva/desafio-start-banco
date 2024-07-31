package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "banco_id")
    private Banco banco;

    // Getters e Setters
}