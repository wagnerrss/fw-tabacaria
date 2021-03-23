package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDocumento(String documento);

    @Query(value = "SELECT * FROM USUARIO WHERE ((EMAIL = :EMAIL) OR (DOCUMENTO = :DOCUMENTO)) AND SENHA = :SENHA", nativeQuery = true)
    public Usuario findByEmailDocumentoSenha(@Param("EMAIL") String email,
                                             @Param("DOCUMENTO") String documento,
                                             @Param("SENHA") String senha);

}
