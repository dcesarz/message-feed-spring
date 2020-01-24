package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.CurrentUser;
import com.example.springProjekt2.domain.Message;
import com.example.springProjekt2.domain.UserState;
import com.example.springProjekt2.service.MessageManager;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageManagerController {

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

    List<Message> getUsersMessages(String nick) {
        List<Message> messageList = mm.findAll();
        List<Message> userMessageList = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getUserList().contains(um.findByNick(cu.getCurrentUser().getNick()))) {
                userMessageList.add(m);
            }
        }
        return userMessageList;
    }

    @GetMapping("message-panel")
    public String messagePanel(final Model model) {
        if (us.isLogged()) {
            model.addAttribute("currentUser", cu);
            model.addAttribute("user_state", us);
            model.addAttribute("message", new Message());
            model.addAttribute("editmsg", new Message());
            List<Message> userMessageList = getUsersMessages(cu.getCurrentUser().getNick());
            model.addAttribute("messages", userMessageList);
            return "message-panel";
        }
        {
            return "redirect:/";
        }
    }

    @GetMapping("message-panel/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id, Model model) {
        if (us.isLogged()) {
            model.addAttribute("currentUser", cu);
            model.addAttribute("userState", us);
            Message m = mm.findById(id);

            if (m == null || m.getUserList() == null || m.getUserList().contains(um.findByNick(cu.getCurrentUser().getNick()))) {
                mm.deleteById(id);
                return "redirect:/message-panel";
            } else {
                return "redirect:/message-panel";
            }
        }
        return "redirect:/";
    }

    @GetMapping("message-panel/edit/{id}")
    public String newEditedMessage(@PathVariable("id") long id, final Model model) {
        if (us.isLogged()) {
            Message m = mm.findById(id);

            if (m != null && m.getUserList() != null && m.getUserList().contains(um.findByNick(cu.getCurrentUser().getNick()))) {
                model.addAttribute("editmsg", mm.findById(id));
                model.addAttribute("oldmsg", mm.findById(id));
                return "message-edit";
            } else {
                return "redirect:/message-panel";
            }
        }
        return "redirect:/";
    }

    @PostMapping("message-panel/edit")
    public String editMessage(@Valid Message editmsg, Errors errors, final Model model, BindingResult bindingResult) {
        System.out.println(editmsg);
        if (errors.hasErrors()) {
            return "message-edit";
        }
        mm.save(editmsg);
        model.addAttribute("editmsg", new Message());
        model.addAttribute("messages", getUsersMessages(cu.getCurrentUser().getNick()));
        return "redirect:/message-panel";
    }


}
