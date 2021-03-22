package com.fw.tabacaria.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "system_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String profile;
    private String document;
    private String picture;
}
