package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Mercadoria;
import com.fw.tabacaria.domain.Usuario;
import com.fw.tabacaria.domain.UsuarioMercadoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioMercadoriaRepository extends CrudRepository<UsuarioMercadoria, Integer> {
    Iterable<UsuarioMercadoria> findByUsuario(Usuario usuario);

    Iterable<UsuarioMercadoria> findByMercadoria(Mercadoria mercadoria);

    @Query(value = "SELECT * FROM USUARIO_MERCADORIA WHERE ID_USUARIO = :ID_USUARIO AND ID_MERCADORIA = :ID_MERCADORIA LIMIT 1 ", nativeQuery = true)
    Optional<UsuarioMercadoria> findByUsuarioMercadoria(@Param("ID_USUARIO") Integer idUsuario,
                                                        @Param("ID_MERCADORIA") Integer idMercadoria);

}
