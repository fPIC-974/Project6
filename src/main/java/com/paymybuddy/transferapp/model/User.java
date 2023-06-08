package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;

    private String email;

    private String password;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Account> accounts = new ArrayList<>();

    public User() {

    }
}

