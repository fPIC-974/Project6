package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.ConnectionRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionService connectionService;

    @Test
    public void getNoConnections() {
        when(connectionRepository.findAll()).thenReturn(null);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> connectionService.getConnections());

        assertEquals("No connections found", runtimeException.getMessage());
    }

    @Test
    public void getConnections() {
        when(connectionRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> connectionRepository.findAll());
    }

    @Test
    public void getConnectionByInvalidId() {
        when(connectionRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> connectionService.getConnectionsById(1));

        assertEquals("Connection not found", notFoundException.getMessage());
    }

    @Test
    public void getConnectionById() {
        when(connectionRepository.findById(anyInt())).thenReturn(Optional.of(new Connection()));

        assertDoesNotThrow(() -> connectionService.getConnectionsById(1));
    }

    @Test
    public void getConnectionByInvalidUserId() {

        when(userRepository.existsById(anyInt())).thenReturn(false);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> connectionService.getConnectionsByUserId(1));

        assertEquals("User not found", notFoundException.getMessage());

        reset(connectionRepository);
    }

    @Test
    public void getConnectionByUserId() {

        when(userRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> connectionService.getConnectionsByUserId(1));
    }

    @Test
    public void saveInvalidArgumentsConnection() {

        NullPointerException nullPointerException = assertThrows(NullPointerException.class,
                () -> connectionService.saveConnection(null, null));

        assertEquals("Invalid parameters", nullPointerException.getMessage());
    }

    @Test
    public void saveNotFoundConnection() {
        User owner = new User("toto", "TOTO", "toto@mail.net", "pass");
        User related = new User("titi", "TITI", "titi@mail.net", "pass");

        owner.setId(1);
        related.setId(2);

//        when(userRepository.findById(1)).thenReturn(Optional.of(owner));
        when(userRepository.findById(2)).thenReturn(Optional.ofNullable(null));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> connectionService.saveConnection(owner, related));

        assertEquals("User not found", notFoundException.getMessage());

    }
}