package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
    Optional<Categoria> findByDescricao(String descricao);
}
