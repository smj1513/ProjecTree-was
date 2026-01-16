package com.ssafy.projectree.domain.user.model.entity;

import com.ssafy.projectree.global.model.entity.BaseEntity;
import com.ssafy.projectree.global.model.enums.OAuthProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;
}