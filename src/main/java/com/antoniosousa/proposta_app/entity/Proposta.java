package com.antoniosousa.proposta_app.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_PROPOSTA")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private boolean integrada;

    private String observacao;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
