package com.github.backend_1st_project.repository.comments;

import com.github.backend_1st_project.web.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsJpaRepository extends JpaRepository<CommentEntity, Integer> {
    @Query("SELECT c FROM CommentEntity c " +
            "INNER JOIN c.post p " +
            "WHERE p.postId = :postId")
    List<CommentEntity> findByPostId(Integer postId);
}
