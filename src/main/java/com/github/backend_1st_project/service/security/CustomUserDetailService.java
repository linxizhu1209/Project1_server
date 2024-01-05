package com.github.backend_1st_project.service.security;


import com.github.backend_1st_project.repository.Role.RolesEntity;
import com.github.backend_1st_project.repository.UserDetails.CustomUserDetails;
import com.github.backend_1st_project.repository.UserRole.UserRoleEntity;
import com.github.backend_1st_project.repository.users.UsersEntity;
import com.github.backend_1st_project.repository.users.UsersJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UsersJpaRepository userJpaRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersEntity users = userJpaRepository.findByEmailEquals(email);


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