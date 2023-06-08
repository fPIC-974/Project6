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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(
            name = "email",
            unique = true,
            nullable = false
    )
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Balance balance;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "user"
    )
    private List<Account> accounts = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accounts = null;
    }
}

