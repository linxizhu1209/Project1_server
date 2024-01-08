package com.github.backend_1st_project.web.controller;

import com.github.backend_1st_project.config.security.JwtTokenProvider;
import com.github.backend_1st_project.models.ResponseModel;
import com.github.backend_1st_project.service.UserService;
import com.github.backend_1st_project.web.dto.ResultResponse;
import com.github.backend_1st_project.web.dto.users.LogoutDTO;
import com.github.backend_1st_project.web.dto.users.RequestUser;
import com.github.backend_1st_project.web.dto.users.LoginDTO;
import com.github.backend_1st_project.web.dto.users.SignupDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"유저 API"})
public class UsersController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation("이메일 비밀번호 입력하여 회원 가입 API")
    @PostMapping("/signup")
    public ResponseModel signUp(@RequestBody SignupDTO signUpDTO){
        boolean isSuccess = userService.signup(signUpDTO);
        return new ResponseModel(isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.");
    }

    @ApiOperation("이메일 비밀번호 입력하여 접속하는 api")
    @PostMapping("/login")
    public ResponseModel loginSuccess(@RequestBody LoginDTO loginDto, HttpServletResponse httpServletResponse){
        String token = userService.login(loginDto);
        httpServletResponse.setHeader("Authorization",token);
        return new ResponseModel("로그인이 성공하였습니다!");
    }


    @ApiOperation("로그아웃 시 토큰으로 접근 불가능하게하는 로직")
    @PostMapping("/logout")
    private ResponseModel logOut(
            @RequestBody LogoutDTO logOutDto,
            HttpServletRequest request){
        String result = userService.logout(request);
        return new ResponseModel(result);
    }



    @GetMapping("/users")
    @ApiOperation(value="유저 정보 전체 조회", notes="유저 정보를 전체조회")
    public ResultResponse findAllUser(){
        List<LoginDTO> users = userService.findAllUser();
        return new ResultResponse(users);
    }

    @GetMapping("/users/{userEmail}")
    @ApiOperation(value="유저 정보 조회", notes="특정 유저 정보를 조회")
    @ApiImplicitParam(name = "userEmail", value = "현재 등록된 유저 id")
    public ResultResponse findByUserId(@PathVariable String userEmail){
        List<LoginDTO> users = userService.findByUser(userEmail);
        return new ResultResponse(users);
    }


}
