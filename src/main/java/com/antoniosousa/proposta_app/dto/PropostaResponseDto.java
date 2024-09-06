package com.antoniosousa.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDto {

    private Long id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cpf;
    private BigDecimal renda;
    private BigDecimal valorSolicitado;
    private int prazoPagamento;
    private Boolean aprovada;
    private String observacao;
}
