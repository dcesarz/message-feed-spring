package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.UserState;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserStateServiceConfig {
    @Bean
    public UserState us() {
        UserState us = new UserState();
        us.setAdmin(false);
        us.setLogged(false);
        return us;
    }
}
