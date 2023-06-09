package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "balance")
@Data
public class Balance {
    @Id
    @Column(name = "id")
    private Integer id;

    private Double amount;

    /*@ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")*/
    @ToString.Exclude
    @OneToOne(mappedBy = "balance")
    private User user;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "balance"
    )
    List<Transfer> transfers = new ArrayList<>();

    public Balance() {

    }

    public Balance(User user) {
        this.amount = 0.00;
        this.user = user;
        this.setId(user.getId());
    }
}

