package com.walther.microservices.dao.repositories;

import com.walther.microservices.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByEdadLessThan(int edad);

    @Query("SELECT u FROM ms_user u WHERE u.edad BETWEEN ?1 AND ?2 AND u.name LIKE %?3%")
    List<UserEntity> findAllUsersBetweenAgesAndName(int minAge, int maxAge, String name);
}