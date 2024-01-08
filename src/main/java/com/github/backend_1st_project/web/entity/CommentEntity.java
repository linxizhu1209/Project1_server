package com.github.backend_1st_project.web.entity;


import com.github.backend_1st_project.web.dto.comments.CommentBody;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "commentId")
@Entity
@Table(name = "comments")
public class CommentEntity extends TimeEntity {
    @Id
    @Column(name = "comment_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Integer likeCount;

    public CommentEntity(String content, PostEntity post, UserEntity user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.content = content;
        this.post = post;
        this.user = user;
        this.likeCount = 0;
    }

    public void setCommentBody(CommentBody postBody){
        this.content = postBody.getContent();
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
