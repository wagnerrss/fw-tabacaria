package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mercadoria")
@Data
public class Mercadoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String foto1;
    private String foto2;
    private String foto3;
    private String nome;
    private String descricao;
    private String ean;
    private Float precoSugerido;
    private String tipo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Categoria categoria;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Marca marca;
}
