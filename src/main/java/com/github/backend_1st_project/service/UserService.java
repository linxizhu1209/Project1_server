package com.github.backend_1st_project.service;

import com.github.backend_1st_project.config.security.JwtTokenProvider;
import com.github.backend_1st_project.repository.Role.RoleJpaRepository;
import com.github.backend_1st_project.repository.Role.RolesEntity;
import com.github.backend_1st_project.repository.UserRole.UserRoleEntity;
import com.github.backend_1st_project.repository.UserRole.UserRoleJpaRepository;
import com.github.backend_1st_project.repository.users.UsersEntity;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import com.github.backend_1st_project.service.exception.InvalidValueException;
import com.github.backend_1st_project.service.exception.NotAcceptException;
import com.github.backend_1st_project.service.exception.NotFoundException;
import com.github.backend_1st_project.web.dto.users.LoginDto;
import com.github.backend_1st_project.web.dto.users.SignUpDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UsersJpaRepository usersJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserRoleJpaRepository userRoleJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    public boolean signup(SignUpDTO signUpDTO) {

            String email = signUpDTO.getEmail();
            String password = signUpDTO.getPassword();

            if(usersJpaRepository.existsByEmail(email)){
                return false;
            }
            RolesEntity role = roleJpaRepository.findByRoleName("ROLE_USER").orElseThrow(()->new NotFoundException("ROLE_USER를 찾을 수 없습니다."));
              UsersEntity user = UsersEntity.builder()
                    .email(email).password(passwordEncoder.encode(password))
                    .createdAt(LocalDateTime.now()).build();

            usersJpaRepository.save(user);
            userRoleJpaRepository.save(UserRoleEntity.builder().role(role).users(user).build());
            return true;
        }

    public String login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String pwd = loginDto.getPassword();

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, pwd)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UsersEntity users = usersJpaRepository.findByEmailEquals(email);
                List<String> roles = users.getUserRoleList().stream().map(UserRoleEntity::getRole).map(RolesEntity::getRoleName).collect(Collectors.toList());
                return jwtTokenProvider.createToken(email, roles);
            } catch (Exception e) {
                e.printStackTrace();
                throw new NotAcceptException("로그인 할 수 없습니다.");
            }
        }

    public void logout(String encryptedToken) {
        if(jwtTokenProvider.validateToken(encryptedToken)){
            jwtTokenProvider.nullifyToken(encryptedToken);
        }
    }

//
}
