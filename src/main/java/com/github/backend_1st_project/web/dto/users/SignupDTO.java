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
@Schema(description = "회원가입 관련 DTO")
public class SignupDTO {
    @Schema(description = "사용자 이메일",nullable = false,example = "example1@naver.com")
    private String email;
    @Schema(description = "사용자 비밀번호",nullable = false,example = "12341234")
    private String password;
}


