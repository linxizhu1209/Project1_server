package com.github.backend_1st_project.web.dto.comments;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Schema(description = "Comment 관련 DTO")
public class CommentBody {
    @Schema(description = "댓글 내용",nullable = false,example = "첫 댓글!")
    private String content;
    @Schema(description = "작성자 이메일",nullable = false,example = "example1@naver.com")
    private String author;
    @Schema(description = "게시물 번호",nullable = false,example = "1")
    private Integer postId;
}

