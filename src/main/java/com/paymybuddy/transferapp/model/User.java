package com.paymybuddy.transferapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@ToString
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

    @Column(name = "role")
    private String role;

    /*@OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn*/
    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "id")
    @JoinColumn(name = "id")
    private Balance balance;

    @ToString.Exclude
    @OneToMany(
//            fetch = FetchType.EAGER,
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<Account> accounts = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    List<Payment> payments = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "connection",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id")
    )
    @JsonIgnore
    private List<User> connections = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}

