package com.github.backend_1st_project.web.dto.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Schema(description = "POST 관련 DTO")
public class PostBody {
    @Schema(description = "제목",nullable = false,example = "타이틀입니다!!!")
    private String title;
    @Schema(description = "내용",nullable = false,example = "내용입니다★")
    private String content;
    @Schema(description = "사용자 이메일",nullable = false,example = "example1@naver.com")
    private String author;
}
