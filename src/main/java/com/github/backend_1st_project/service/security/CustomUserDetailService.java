package com.github.backend_1st_project.service.security;

import com.github.backend_1st_project.repository.userDetails.CustomUserDetails;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import com.github.backend_1st_project.web.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.github.backend_1st_project.web.entity.RolesEntity;
import com.github.backend_1st_project.web.entity.UserRoleEntity;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UsersJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity users = userJpaRepository.findByEmailEquals(email);

        return CustomUserDetails.builder()
                .userId(users.getUserId())
                .email(users.getEmail())
                .password(users.getPassword())
                .authorities(users.getUserRoleList().stream()
                        .map(UserRoleEntity::getRole)
                        .map(RolesEntity::getRoleName)
                        .collect(Collectors.toList())).build();


    }
}
