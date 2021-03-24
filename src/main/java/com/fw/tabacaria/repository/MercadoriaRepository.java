package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Mercadoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MercadoriaRepository extends CrudRepository<Mercadoria, Integer> {
    Iterable<Mercadoria> findByIdUsuario(Integer idUsuario);

    Iterable<Mercadoria> findByIdCategoria(Integer idCategoria);

    Optional<Mercadoria> findByNome(String nome);

    Optional<Mercadoria> findByDescricao(String descricao);

    @Query(value = "SELECT * FROM MERCADORIA WHERE ((UPPER(NOME) CONTAINING UPPER(:NOME)) OR (UPPER(DESCRICAO) CONTAINING UPPER(:DESCRICAO))) ", nativeQuery = true)
    Iterable<Mercadoria> findByNomeDescricao(@Param("NOME") String nome,
                                             @Param("DESCRICAO") String descricao);

    @Query(value = "SELECT * FROM MERCADORIA WHERE ID_USUARIO = :ID_USUARIO AND NOME = :NOME LIMIT 1 ", nativeQuery = true)
    Optional<Mercadoria> findByIdUsuarioNome(@Param("ID_USUARIO") Integer idUsuario,
                                             @Param("NOME") String nome);

}
