package com.github.backend_1st_project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    public static final String TITLE_NAME = "Bearer Token";

// Security 스키마 설정
    SecurityScheme bearerAuth = new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .scheme("bearer")
        .bearerFormat("Authorization")
        .in(SecurityScheme.In.HEADER)
        .name(HttpHeaders.AUTHORIZATION);

 // Security 요청 설정
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(TITLE_NAME);



    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("1차 프로젝트 게시판 REST API Document(refactoring)")
                .version("v0.0.1")
                .description("1차 프로젝트 리팩터링하여 만든 swagger입니다(2024.1.15)");
        return new OpenAPI().components(new Components().addSecuritySchemes(TITLE_NAME,bearerAuth)).security(Arrays.asList(securityRequirement)).info(info);
    }

    @Bean
    public GroupedOpenApi User(){
        return GroupedOpenApi.builder().group("user")
                .pathsToMatch("/api/user/**").
                packagesToScan("com.github.backend_1st_project.web.controller.UsersController").build();
    }

    @Bean
    public GroupedOpenApi Post(){
        return GroupedOpenApi.builder().group("post")
                .pathsToMatch("/api/posts/**").
                packagesToScan("com.github.backend_1st_project.web.controller.PostsController").build();
    }

    @Bean
    public GroupedOpenApi Comments(){
        return GroupedOpenApi.builder().group("comments")
                .pathsToMatch("/api/comments/**").
                packagesToScan("com.github.backend_1st_project.web.controller.CommentsController").build();
    }

}
