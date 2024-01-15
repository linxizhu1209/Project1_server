package com.github.backend_1st_project.web.dto.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "POST조회 응답형식 관련 DTO")
public class PostsDTO {
    @Schema(description = "게시물번호",nullable = false,example = "1")
    private Integer postId;
    @Schema(description = "제목",nullable = false,example = "제목입니다!")
    private String title;
    @Schema(description = "내용",nullable = false,example = "내용입니당~")
    private String content;
    @Schema(description = "작성자이메일",nullable = false,example = "example@gmail.com")
    private String author;
    @Schema(description = "조회수",nullable = false,example = "15")
    private Integer viewCount;
    @Schema(description = "좋아요",nullable = false,example = "3")
    private Integer likeCount;
    @Schema(description = "작성일시",nullable = false,example = "2024-1-15")
    private String createdAt;
}
