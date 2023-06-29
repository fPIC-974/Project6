package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.ConnectionRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService implements IConnectionService {

    private final ConnectionRepository connectionRepository;

    private final UserRepository userRepository;

    public ConnectionService(ConnectionRepository connectionRepository,
                             UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
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
    public List<User> getConnectionsByUserId(int id) {
        List<User> connections = new ArrayList<>();

        for(Connection connection: connectionRepository.getConnectionsByUserId(id)) {
            connections.add(userRepository.findById(connection.getConnectionId()).orElse(null));
        }

        return connections;
    }

    @Override
    public Connection saveConnection(User owner, User related) {
        if (related == null || userRepository.findById(related.getId()).isEmpty()) {
            throw new RuntimeException("Connection not found");
        }

        return connectionRepository.save(new Connection(owner.getId(), related.getId()));
    }
}
