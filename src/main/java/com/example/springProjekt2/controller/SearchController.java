package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.CurrentUser;
import com.example.springProjekt2.domain.UserState;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SearchController {
    private UserState us;
    private CurrentUser cu;
    private UserManager um;

    @Autowired
    public void setUs(UserState us) {
        this.us = us;
    }

    @Autowired
    public void setCu(CurrentUser cu) {
        this.cu = cu;
    }

    @Autowired
    public void setUm(UserManager um) {
        this.um = um;
    }
}
