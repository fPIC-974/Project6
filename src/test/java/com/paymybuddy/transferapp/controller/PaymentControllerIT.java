package com.paymybuddy.transferapp.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser("toto@mail.net")
    public void testAddPaymentNoFunds() throws Exception {
        mockMvc.perform(post("/payments/addNew")
                        .param("user", "1")
                        .param("id", "2")
                        .param("amount", "100000")
                        .param("description", "Test"))
                .andDo(print())
                .andExpect(view().name("redirect:/transactions"))
                .andExpect(redirectedUrl("/transactions?currentPage=1&errorMessage=Insufficient+funds&pageNumbers=1&pageNumbers=2&pageNumbers=3&pageNumbers=4&user=1"))
                .andExpect(model().attribute("errorMessage", "Insufficient funds"));
    }

    @Test
    @WithMockUser("toto@mail.net")
    @Transactional
    public void testAddPayment() throws Exception {
        mockMvc.perform(post("/payments/addNew")
                        .param("user", "1")
                        .param("id", "2")
                        .param("amount", "23")
                        .param("description", "Test"))
                .andDo(print())
                .andExpect(view().name("redirect:/transactions"))
                .andExpect(redirectedUrl("/transactions?currentPage=1&statusMessage=Payment+done&pageNumbers=1&pageNumbers=2&pageNumbers=3&pageNumbers=4&user=1"))
                .andExpect(model().attribute("statusMessage", "Payment done"));
    }
}