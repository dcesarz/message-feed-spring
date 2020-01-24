package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.CurrentUser;
import com.example.springProjekt2.domain.User;
import com.example.springProjekt2.domain.UserLogin;
import com.example.springProjekt2.domain.UserState;
import com.example.springProjekt2.service.MessageManager;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ProfileController {

    private UserState us;
    private CurrentUser cu;
    private UserManager um;
    private MessageManager mm;

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

    @Autowired
    public void setMm(MessageManager mm) {
        this.mm = mm;
    }

    @GetMapping("profile")
    public String userPanel(final Model model) {
        model.addAttribute("currentUser", cu);
        if (us.isLogged()) {
            User newUser = cu.getCurrentUser();
            model.addAttribute("user", newUser);
            model.addAttribute("user_state", us);
            model.addAttribute("currentUser", cu.getCurrentUser());
            model.addAttribute("cuid", cu.getCurrentUser().getId());
            return "user-profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("profile")
    public String saveChanges(@Valid @ModelAttribute("user") User user, BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "user-profile";
        }
        User newUser = um.findByNick(cu.getCurrentUser().getNick());
        model.addAttribute("userLogin", new UserLogin(user.getNick(), user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setNick(user.getNick());
        um.save(newUser);
        System.out.println(newUser);
        cu.setCurrentUser(um.findByNick(newUser.getNick()));
        return "user-profile";
    }

    @PostMapping("profile/delete")
    public String deleteUsersAccount(@RequestParam long id) {
        if (us.isLogged()) {
            mm.deleteByUserList_Id(id);
            um.delete(um.findById(id));
            return "redirect:/timeline/logout";
        } else {
            return "redirect:/";
        }
    }

}
