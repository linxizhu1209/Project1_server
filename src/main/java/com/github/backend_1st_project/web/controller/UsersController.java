package com.github.backend_1st_project.web.controller;


import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.service.UserService;
import com.github.backend_1st_project.web.dto.users.LogoutDTO;
import com.github.backend_1st_project.web.dto.users.LoginDTO;
import com.github.backend_1st_project.web.dto.users.SignupDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name="User",description = "유저 API Document")
public class UsersController {

    private final UserService userService;


    @Operation(summary = "회원가입", description = "이메일 비밀번호 입력하여 회원가입")
    @PostMapping("/signup")
    public ResponseModel signUp(@RequestBody SignupDTO signUpDTO){
        boolean isSuccess = userService.signup(signUpDTO);
        return new ResponseModel(isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.");
    }

    @Operation(summary = "로그인",description = "이메일 비밀번호를 입력하여 로그인" )
    @PostMapping(value = "/login", headers = {
            "Content-type=application/json"
    })
    public ResponseModel loginSuccess(@RequestBody LoginDTO loginDto, HttpServletResponse httpServletResponse){
        String token = userService.login(loginDto);
        httpServletResponse.setHeader("Authorization",token);
        return new ResponseModel("로그인이 성공하였습니다!");
    }


    @Operation(summary = "로그아웃",description = "이메일 입력하여 로그아웃")
    @PostMapping("/logout")
    private ResponseModel logOut(
            @RequestBody LogoutDTO logOutDto,
            HttpServletRequest request){
        String result = userService.logout(request);
        return new ResponseModel(result);
    }
}

