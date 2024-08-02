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

    public Banco atualizarBanco(Long id, Banco banco) {
        if (bancoRepository.existsById(id)) {
            banco.setId(id);
            return bancoRepository.save(banco);
        }
        return null;
    }

    public boolean removerBanco(Long id) {
        if (bancoRepository.existsById(id)) {
            bancoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
