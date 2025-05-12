package com.habittracke.dto.response;

import com.habittracke.entity.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class UserDTO {
    private String id;

    private String username;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private AccountStatus status = AccountStatus.NEW;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO(String id, String username, String firstName, String lastName, String email, String phone, AccountStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.status = status != null ? status : AccountStatus.NEW; // Nếu không có status, sử dụng giá trị mặc định
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
