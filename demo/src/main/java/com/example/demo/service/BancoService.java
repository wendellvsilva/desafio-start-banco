package com.example.demo.service;

import com.example.demo.model.Banco;
import com.example.demo.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoService {
    @Autowired
    private BancoRepository bancoRepository;

    public Banco criarBanco(Banco banco) {
        return bancoRepository.save(banco);
    }

    public List<Banco> listarBancos() {
        return bancoRepository.findAll();
    }

    public Banco buscarBancoPorId(Long id) {
        return bancoRepository.findById(id).orElse(null);
    }
    public boolean verificarSenhaBanco(Long id, String senha) {
        Banco banco = bancoRepository.findById(id).orElse(null);
        return banco != null && banco.getSenha().equals(senha);
    }
}
