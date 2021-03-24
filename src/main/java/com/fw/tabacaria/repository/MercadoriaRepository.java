package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Categoria;
import com.fw.tabacaria.domain.Marca;
import com.fw.tabacaria.domain.Mercadoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MercadoriaRepository extends CrudRepository<Mercadoria, Integer> {
    Iterable<Mercadoria> findByCategoria(Categoria categoria);

    Iterable<Mercadoria> findByMarca(Marca marca);

    Optional<Mercadoria> findByNome(String nome);

    Optional<Mercadoria> findByDescricao(String descricao);

    Optional<Mercadoria> findByEan(String ean);

    @Query(value = "SELECT * FROM MERCADORIA WHERE ((UPPER(NOME) CONTAINING UPPER(:NOME)) OR (UPPER(DESCRICAO) CONTAINING UPPER(:DESCRICAO))) ", nativeQuery = true)
    Iterable<Mercadoria> findByNomeDescricao(@Param("NOME") String nome,
                                             @Param("DESCRICAO") String descricao);

}
