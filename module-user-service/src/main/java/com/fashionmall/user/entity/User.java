package com.fashionmall.user.entity;

import com.fashionmall.common.jwt.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // 기본설정 되는지 확인하기
    private UserRoleEnum role;

    @Builder
    public User(String email, String password, String userName, String nickName, String contact, UserRoleEnum role) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.nickName = nickName;
        this.contact = contact;
        this.role = role;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickName) {
        this.nickName = nickName;
    }

    public void updateContact(String contact) {
        this.contact = contact;
    }
}
