package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Data
public class MercadoriaCompleta {
    private Integer id;
    private Usuario usuario;
    private Categoria categoria;
    private String foto;
    private String nome;
    private String descricao;
    private Float preco;
    private String tipo;
    private Float quantidade;
}
