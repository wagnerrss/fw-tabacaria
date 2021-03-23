package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Endereco;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EnderecoRepository extends CrudRepository<Endereco, Integer> {
    Iterable<Endereco> findByIdUsuario(Integer idUsuario);
}
