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
    private Integer idUsuario;
    private Integer idCategoria;
    private String foto;
    private String nome;
    private String descricao;
    private Float preco;
    private String tipo;
    private Float quantidade;
}
