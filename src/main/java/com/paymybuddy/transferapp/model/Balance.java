package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "balance")
@Data
public class Balance {
    @Id
    private Integer id;

    private Double amount;

    @ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public Balance() {

    }

    public Balance(User user) {
        this.amount = 0.00;
        this.user = user;
    }
}

