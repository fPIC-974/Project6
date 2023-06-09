package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount;

    @CreationTimestamp
    private LocalDateTime date;

    private String description;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Payment() {

    }

    public Payment(double amount, String description, Balance balance, User user) {
        this.amount = amount;
        this.description = description;
        this.balance = balance;
        this.user = user;
    }
}

