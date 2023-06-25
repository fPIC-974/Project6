package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer number;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "account"
    )
    List<Transfer> transfers = new ArrayList<>();

    public Account() {

    }

    public Account(String name, Integer number, User user) {
        this.name = name;
        this.number = number;
        this.user = user;
    }
}

