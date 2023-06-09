package com.paymybuddy.transferapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ConnectionId implements Serializable {
    private int userId;
    private int connectionId;

    /*public ConnectionId(int userId, int connectionId) {
        this.userId = userId;
        this.connectionId = connectionId;
    }*/
}
