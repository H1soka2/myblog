package ru.myblog.usersservice.repository;

import ru.myblog.commonlib.user.Role;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "final_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "user_name", unique = true, nullable = false)
    String username;

    @Column(name = "email",unique = true, nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "about me", length = 255)
    String aboutMe;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;
}
