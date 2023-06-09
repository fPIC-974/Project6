package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.ConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService implements IConnectionService {

    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public List<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public Connection getConnectionsById(int id) {
        return connectionRepository.findById(id).orElse(null);
    }

    @Override
    public Connection saveConnection(User owner, User related) {
        return connectionRepository.save(new Connection(owner.getId(), related.getId()));
    }
}
