package com.github.backend_1st_project.web.dto.users;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "로그아웃 관련 DTO")
public class LogoutDTO {
    @Schema(description = "사용자 이메일",nullable = false,example = "example1@naver.com")
    private String email;
}

