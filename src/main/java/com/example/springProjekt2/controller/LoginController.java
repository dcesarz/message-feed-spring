package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.CurrentUser;
import com.example.springProjekt2.domain.User;
import com.example.springProjekt2.domain.UserLogin;
import com.example.springProjekt2.domain.UserState;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
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

    @GetMapping("/")
    public String home(UserLogin userLogin, final Model model) {
        model.addAttribute("userLogin", new UserLogin());
        if (us.isLogged()) {
            return "redirect:/timeline";
        }
        return "login";
    }

    @PostMapping("login")
    public String login(@Valid @ModelAttribute("userLogin") UserLogin userLogin, BindingResult result, final Model model) {
        User get = um.findByNick(userLogin.getNick());
        if (get != null && get.getPassword().equals(userLogin.getPassword())) {
            us.setLogged(true);
            if (get.getNick().equals("administrator")) {
                us.setAdmin(true);
            }
            cu.setCurrentUser(get);
            return "redirect:/timeline";
        }
        return "login";
    }


    @GetMapping("signup")
    public String signUpScreen(final Model model) {
        model.addAttribute("user", new User());
        if (us.isLogged()) {
            return "redirect:/timeline";
        }
        return "signup";
    }

    @PostMapping("signup")
    public String signUp(@Valid @ModelAttribute("user") User user, BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        System.out.println(user);
        model.addAttribute("userLogin", new UserLogin(user.getNick(), user.getPassword()));
        um.save(user);
        //...technicznie to zazwyczaj logowanie odbywa sie oddzielnie od rejestracji więc:
        return "login";
    }
}
