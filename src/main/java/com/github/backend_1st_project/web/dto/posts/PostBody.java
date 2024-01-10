package com.github.backend_1st_project.web.dto.posts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class PostBody {
    @ApiModelProperty(example = "타이틀입니다!!!")
    private String title;
    @ApiModelProperty(example = "내용입니다★")
    private String content;
    @ApiModelProperty(example = "example1@naver.com")
    private String author;
}
