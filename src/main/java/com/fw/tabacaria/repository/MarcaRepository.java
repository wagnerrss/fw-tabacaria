package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Marca;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MarcaRepository extends CrudRepository<Marca, Integer> {
    Optional<Marca> findByNome(String nome);
}
