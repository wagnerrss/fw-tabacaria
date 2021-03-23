package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String foto;
    private String nome;
    private String documento;
    private String email;
    private String telefone;
    private String senha;
    private String tipo;
    private String token;
}
