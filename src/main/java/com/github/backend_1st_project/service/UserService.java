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
import com.github.backend_1st_project.service.mapper.UserMapper;
import com.github.backend_1st_project.web.dto.users.LoginDto;
import com.github.backend_1st_project.web.dto.users.SignUpDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
            RolesEntity role = roleJpaRepository.findByRoleName("user").orElseThrow(()->new NotFoundException("사용자 권한을 찾을 수 없습니다."));
              UsersEntity user = UsersEntity.builder()
                    .email(email).password(passwordEncoder.encode(password))
                    .build();

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

    public String logout(HttpServletRequest request) {
        String encryptedToken = jwtTokenProvider.resolveToken(request);
// 컨트롤러의 로직을 서비스로 이동
        if(jwtTokenProvider.validateToken(encryptedToken)){
            jwtTokenProvider.nullifyToken(encryptedToken);
        }
        return "로그아웃이 성공적으로 완료 되었습니다!";
    }


    public List<LoginDto> findByUser(String userEmail) {
        List<UsersEntity> userEntity = usersJpaRepository.findByEmail(userEmail);
        if(userEntity.isEmpty())
            throw new NotFoundException("해당 ID: " + userEmail + "를 찾을 수 없습니다.");
        List<LoginDto> userDto = userEntity.stream().map(UserMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return userDto;
    }

    public List<LoginDto> findAllUser() {
        List<UsersEntity> userEntity = usersJpaRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
        List<LoginDto> userDto = userEntity.stream().map(UserMapper.INSTANCE::entityToDTO).collect(Collectors.toList());
        return userDto;
    }
//
//    public String saveUser(RequestUser userBody) {
//        UsersEntity userEntity = usersJpaRepository.findByEmailEquals(userBody.getEmail());
//        if(userEntity != null)
//            throw new NotFoundException("해당 ID: " + userEntity.getEmail() + "는 이미 존재합니다.");
//
//        return "회원가입이 완료되었습니다.";
//    }
//


//
}
