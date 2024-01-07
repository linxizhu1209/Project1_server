package com.github.backend_1st_project.repository.users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsersJpaRepository extends JpaRepository<UsersEntity, Integer> {

    @Query("SELECT c FROM UsersEntity c WHERE c.email = :email")
    List<UsersEntity> findByEmail(String email);


    //    UserEntity findByUserEmail(String email);

    boolean existsByEmail(String email);

//    @Query("SELECT c FROM UserEntity c WHERE c.email = :email")
    UsersEntity findByEmailEquals(String email);
}
