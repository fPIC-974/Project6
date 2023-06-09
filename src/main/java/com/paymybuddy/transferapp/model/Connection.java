package com.paymybuddy.transferapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@IdClass(ConnectionId.class)
@Table(name = "connection")
@Data
public class Connection {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "connection_id")
    private Integer connectionId;

    public Connection() {}

    public Connection(int userId, int connectionId) {
        this.userId = userId;
        this.connectionId = connectionId;
    }
}
