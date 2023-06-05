package com.example.aquafarm.User.Domain;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ID")
    private int userId;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;
    private String password;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}