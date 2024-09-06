package com.antoniosousa.proposta_app.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropostaRequestDto {

    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private BigDecimal renda;
    private BigDecimal valorSolicitado;
    private int prazoPagamento;
}
