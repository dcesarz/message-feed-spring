package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentUserServiceConfig {
    @Bean
    public CurrentUser cu() {
        return new CurrentUser();
    }
}
