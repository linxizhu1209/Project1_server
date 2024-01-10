package com.github.backend_1st_project.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "likesId")
@Entity
@Table(name = "likes")
public class LikesEntity{
    @Id
    @Column(name = "likes_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likesId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}

