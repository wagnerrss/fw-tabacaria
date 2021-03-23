package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
}
