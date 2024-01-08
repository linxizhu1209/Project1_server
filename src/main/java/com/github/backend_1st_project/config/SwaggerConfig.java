package com.github.backend_1st_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String TITLE_NAME = "Bearer Token";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.github.backend_1st_project.web.controller"))
                // 파일 경로도 있지만 내가 만든 파일 중에 @RestController를 선언한 파일을 찾아서 나열하게끔 선언
                .apis((RequestHandlerSelectors.withClassAnnotation(RestController.class)))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                // 해당 부분은 swagger2 버전 기준 토큰 설정 및 세팅 법이라서 알아두면 좋을 것 같아요
                // 물론 swagger3 버전으로 가면 다를 수 있습니다.
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("1차 프로젝트 게시판 REST API Document")
                .description("2024.01.02 ~ 2024.01.08")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey(TITLE_NAME, "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(TITLE_NAME, authorizationScopes));
    }
}
