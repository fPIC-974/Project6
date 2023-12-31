package com.paymybuddy.transferapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser("user0@mail.net")
    public void testGetTransactions() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"))
                .andExpect(content().string(containsString("transaction 1")));
    }
}