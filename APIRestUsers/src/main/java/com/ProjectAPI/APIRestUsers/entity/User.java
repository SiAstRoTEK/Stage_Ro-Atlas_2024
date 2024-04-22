package com.ProjectAPI.APIRestUsers.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "UserList")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="Name", nullable = false)
    private String name;

    @Column(name ="Surname", nullable = false)
    private String surname;

    @Column(name ="Username", nullable = false, unique = true)
    private String username;

    @Column(name ="Password", nullable = false)
    private String password;

    @Column(name ="Email", nullable = false, unique = true)
    private String email;
}
