package com.github.backend_1st_project.web.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
@Builder
public class RolesEntity {
    @Id
    @Column(name="role_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name="role_name", length = 30)
    private String roleName;


}
