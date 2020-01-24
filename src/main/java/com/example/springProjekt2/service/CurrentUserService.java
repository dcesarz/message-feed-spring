package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private CurrentUser cu;

    public CurrentUser getCu() {
        return cu;
    }

    @Autowired
    public void setCu(CurrentUser cu) {
        this.cu = cu;
    }
}
