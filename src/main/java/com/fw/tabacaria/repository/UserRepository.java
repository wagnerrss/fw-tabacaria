package com.fw.tabacaria.repository;

import com.fw.tabacaria.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String name);

    @Query(value = "SELECT * FROM SYSTEM_USER WHERE EMAIL = :EMAIL AND PASSWORD = :PASSWORD", nativeQuery = true)
    public User findByEmailPassword(@Param("EMAIL") String EMAIL,
                                    @Param("PASSWORD") String PASSWORD);

    Optional<User> findByEmail(String email);
}
