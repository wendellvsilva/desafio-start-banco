package com.example.demo.dto;

import com.example.demo.model.Banco;
import com.example.demo.model.Conta;
import com.example.demo.model.Movimentacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovimentacaoDTO {
    private Long id;
    private LocalDateTime data;
    private String operacao;
    private Double valor;
    private Long contaId;
    private Long bancoId;
    private Long contaDestinoId;

    public MovimentacaoDTO() {}

    public MovimentacaoDTO(Movimentacao movimentacao) {
        this.id = movimentacao.getId();
        this.data = movimentacao.getData();
        this.operacao = movimentacao.getOperacao();
        this.valor = movimentacao.getValor();
        this.contaId = movimentacao.getConta().getId();
        this.bancoId = movimentacao.getBanco().getId();
        this.contaDestinoId = movimentacao.getContaDestino() != null ? movimentacao.getContaDestino().getId() : null;
    }

    public Movimentacao toMovimentacao(Conta conta, Banco banco, Conta contaDestino) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(this.id);
        movimentacao.setData(this.data);
        movimentacao.setOperacao(this.operacao);
        movimentacao.setValor(this.valor);
        movimentacao.setConta(conta);
        movimentacao.setBanco(banco);
        movimentacao.setContaDestino(contaDestino);
        return movimentacao;
    }
}
