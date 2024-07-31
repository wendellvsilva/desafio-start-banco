package com.example.demo.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("POU")
public class ContaPoupanca extends Conta {
    private double taxaJuros;
}