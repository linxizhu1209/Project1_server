package com.github.backend_1st_project.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "userEmail")
@Entity
@Table(name = "users")
public class UserEntity extends TimeEntity {
    @Id
    @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "user_pwd")
    private String password;
    @Column(name = "user_email", length = 30)
    private String email;
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoleList;

    public UserEntity(LocalDateTime createdAt, LocalDateTime updatedAt, Integer userId, String password, String email, List<UserRoleEntity> userRoleList) {
        super(createdAt, updatedAt);
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userRoleList = userRoleList;
    }
}
