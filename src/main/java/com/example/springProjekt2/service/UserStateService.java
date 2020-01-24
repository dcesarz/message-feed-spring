package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserStateService {
    private UserState us;

    UserState getUs() {
        return us;
    }

    @Autowired
    public void setUs(UserState us) {
        this.us = us;
    }
}
