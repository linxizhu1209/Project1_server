package com.github.backend_1st_project.web.dto.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
public class PostsDTO {
    private Integer postId;
    private String title;
    private String content;
    private String author;
    private Integer viewCount;
    private Integer likeCount;
    private String createdAt;
}
