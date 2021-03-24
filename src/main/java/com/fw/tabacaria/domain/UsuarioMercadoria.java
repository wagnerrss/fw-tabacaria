package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuario_mercadoria")
@Data
public class UsuarioMercadoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Float preco;
    private Float quantidade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Mercadoria mercadoria;
}
