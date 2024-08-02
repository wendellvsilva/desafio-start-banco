package com.example.demo.controller;

import com.example.demo.dto.BancoDTO;
import com.example.demo.model.Banco;
import com.example.demo.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @PostMapping
    public ResponseEntity<BancoDTO> criarBanco(@RequestBody BancoDTO bancoDTO) {
        Banco banco = bancoDTO.toBanco();
        Banco novoBanco = bancoService.criarBanco(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BancoDTO(novoBanco));
    }

    @GetMapping
    public ResponseEntity<List<BancoDTO>> listarBancos() {
        List<Banco> bancos = bancoService.listarBancos();
        List<BancoDTO> bancoDTOs = bancos.stream().map(BancoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(bancoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoDTO> buscarBancoPorId(@PathVariable Long id) {
        Banco banco = bancoService.buscarBancoPorId(id);
        if (banco != null) {
            return ResponseEntity.ok(new BancoDTO(banco));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BancoDTO> atualizarBanco(@PathVariable Long id, @RequestBody BancoDTO bancoDTO) {
        Banco banco = bancoDTO.toBanco();
        Banco bancoAtualizado = bancoService.atualizarBanco(id, banco);
        if (bancoAtualizado != null) {
            return ResponseEntity.ok(new BancoDTO(bancoAtualizado));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerBanco(@PathVariable Long id) {
        boolean removido = bancoService.removerBanco(id);
        if (removido) {
            return ResponseEntity.ok("Banco removido com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Banco não encontrado.");
        }
    }
}
