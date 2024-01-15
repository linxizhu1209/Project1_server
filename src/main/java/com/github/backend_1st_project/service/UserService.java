package com.github.backend_1st_project.service;

import com.github.backend_1st_project.config.security.JwtTokenProvider;
import com.github.backend_1st_project.repository.users.RoleJpaRepository;
import com.github.backend_1st_project.repository.users.UserRoleJpaRepository;
import com.github.backend_1st_project.service.exception.NotAcceptException;
import com.github.backend_1st_project.web.dto.users.LoginDTO;
import com.github.backend_1st_project.web.dto.users.SignupDTO;
import com.github.backend_1st_project.web.entity.RolesEntity;
import com.github.backend_1st_project.web.entity.UserEntity;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import com.github.backend_1st_project.service.exception.NotFoundException;
import com.github.backend_1st_project.web.entity.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersJpaRepository usersJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserRoleJpaRepository userRoleJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public boolean signup(SignupDTO signUpDTO) {

        String email = signUpDTO.getEmail();
        String password = signUpDTO.getPassword();

        if(usersJpaRepository.existsByEmail(email)){
            return false;
        }

        RolesEntity role = roleJpaRepository.findByRoleName("user").orElseThrow(()->new NotFoundException("사용자 권한을 찾을 수 없습니다."));
        UserEntity user = UserEntity.builder()
                .email(email).password(passwordEncoder.encode(password)).build();

        usersJpaRepository.save(user);
        userRoleJpaRepository.save(UserRoleEntity.builder().role(role).users(user).build());
        return true;
    }

    public String login(LoginDTO loginDto) {
        String email = loginDto.getEmail();
        String pwd = loginDto.getPassword();
        UserEntity users = usersJpaRepository.findByEmailEquals(email);
        if(users == null)
          throw new NotFoundException("아이디 혹은 비밀번호가 틀렸습니다.");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, pwd)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            List<String> roles = users.getUserRoleList().stream().map(UserRoleEntity::getRole).map(RolesEntity::getRoleName).collect(Collectors.toList());
            return jwtTokenProvider.createToken(email, roles);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptException("로그인 할 수 없습니다.");
        }
    }

    public String logout(HttpServletRequest request) {
        String encryptedToken = jwtTokenProvider.resolveToken(request);
        if(jwtTokenProvider.validateToken(encryptedToken)){
            jwtTokenProvider.nullifyToken(encryptedToken);
        }
        return "로그아웃이 성공적으로 완료 되었습니다!";
    }
}
