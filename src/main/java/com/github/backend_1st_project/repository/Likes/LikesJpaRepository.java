package com.github.backend_1st_project.repository.Likes;

import com.github.backend_1st_project.web.entity.LikesEntity;
import com.github.backend_1st_project.web.entity.PostEntity;
import com.github.backend_1st_project.web.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesJpaRepository extends JpaRepository<LikesEntity, Integer> {

   boolean existsByUserEqualsAndPostEquals(UserEntity user,PostEntity post);

   LikesEntity findLikesEntityByPostEqualsAndUserEquals(PostEntity post,UserEntity user);

}
