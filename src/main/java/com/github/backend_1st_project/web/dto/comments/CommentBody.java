package com.github.backend_1st_project.web.dto.comments;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class CommentBody {
    @ApiModelProperty(example = "첫 댓글!!")
    private String content;
    @ApiModelProperty(
            name = "작성자",
            example = "example1@naver.com")
    private String author;
    @ApiModelProperty(example = "1")
    private Integer postId;
}
