package com.paymybuddy.transferapp.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConnectionControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser("toto@mail.net")
    public void testAddNonExistingConnection() throws Exception {
        mockMvc.perform(post("/connections/addNew")
                        .param("id", "1")
                        .param("email", "noOne"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"))
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    @WithMockUser("toto@mail.net")
    @Transactional
    public void testAddExistingConnection() throws Exception {
        mockMvc.perform(post("/connections/addNew")
                        .param("id", "1")
                        .param("email", "tyty@mail.net"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"))
                .andExpect(content().string(containsString("Connection added")))
                .andExpect(content().string(containsString("tyty@mail.net")));
    }
}