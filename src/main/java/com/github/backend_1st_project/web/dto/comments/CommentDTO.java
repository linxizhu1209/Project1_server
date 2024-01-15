package com.github.backend_1st_project.web.dto.comments;

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
@Schema(description = "Comment조회 응답형식 관련 DTO")
public class CommentDTO {
    @Schema(description = "댓글번호",nullable = false,example = "1")
    private Integer id;
    @Schema(description = "댓글내용",nullable = false,example = "댓글내용입니당")
    private String content;
    @Schema(description = "댓글작성자이메일",nullable = false,example = "example@naver.com")
    private String author;
    @Schema(description = "게시물번호",nullable = false,example = "1")
    private Integer postId;
    @Schema(description = "댓글작성일자",nullable = false,example = "2024-01-15")
    private String createdAt;
}

