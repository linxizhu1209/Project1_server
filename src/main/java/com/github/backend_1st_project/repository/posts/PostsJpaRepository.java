package com.github.backend_1st_project.repository.posts;

import com.github.backend_1st_project.web.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsJpaRepository extends JpaRepository<PostEntity, Integer> {

    @Query("SELECT c FROM PostEntity c " +
            "INNER JOIN c.user u " +
            "WHERE u.email = :userEmail")
    List<PostEntity> findByUserEmail(String userEmail);

    PostEntity findByPostId(Integer postId);
}
