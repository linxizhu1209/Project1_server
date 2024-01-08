package com.github.backend_1st_project.web.entity;

import com.github.backend_1st_project.web.dto.posts.PostBody;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "postId")
@Entity
@Table(name = "posts")
public class PostEntity extends TimeEntity {
    @Id
    @Column(name = "post_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "view_count", columnDefinition = "DEFAULT 0")
    private Integer viewCount;

    @Column(name = "like_count", columnDefinition = "DEFAULT 0")
    private Integer likeCount;

    public PostEntity(String title, String content, UserEntity user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.title = title;
        this.viewCount = 0;
        this.likeCount = 0;
        this.content = content;
        this.user = user;
    }

    public void setPostBody(PostBody postBody){
        this.title = postBody.getTitle();
        this.content = postBody.getContent();
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
