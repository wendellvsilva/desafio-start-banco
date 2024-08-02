package com.example.demo.dto;

import com.example.demo.model.Banco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoDTO {
    private Long id;
    private String nome;
    private String senha;

    public BancoDTO() {}

    public BancoDTO(Banco banco) {
        this.id = banco.getId();
        this.nome = banco.getNome();
        this.senha = banco.getSenha();
    }

    public Banco toBanco() {
        Banco banco = new Banco();
        banco.setId(this.id);
        banco.setNome(this.nome);
        banco.setSenha(this.senha);
        return banco;
    }
}
