package com.habittracke.entity.sql;

import com.habittracke.entity.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class User {
    @Id
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false, length = 50)
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.NEW;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String id, String username, String password, String firstName, String lastName, String email, String phone, AccountStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.status = status != null ? status : AccountStatus.NEW; // Nếu không có status, sử dụng giá trị mặc định
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
