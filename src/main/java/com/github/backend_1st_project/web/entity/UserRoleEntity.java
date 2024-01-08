package com.github.backend_1st_project.web.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users_role")
@Builder
public class UserRoleEntity {

    @Id
    @Column(name="users_role_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private UserEntity users;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private RolesEntity role;
}