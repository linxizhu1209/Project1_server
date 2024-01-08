package com.github.backend_1st_project.repository.users;

import com.github.backend_1st_project.web.entity.RolesEntity;
import com.github.backend_1st_project.web.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity,Integer> {
}
