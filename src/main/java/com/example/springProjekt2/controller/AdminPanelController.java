package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.*;
import com.example.springProjekt2.service.MessageManager;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminPanelController {

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

    @GetMapping("admin-panel")
    public String adminPanel(@RequestParam(required = false) UserSearch nick, final Model model) {
        if (us.isLogged() && us.isAdmin()) {
            System.out.println(nick);
            model.addAttribute("nick", new UserSearch());
            model.addAttribute("user", new User());
            if (nick == null || nick.getNick().isEmpty() || nick.getNick() == null) {
                model.addAttribute("users", um.findAll());
            } else {
                model.addAttribute("users", um.findAllByNick(nick.getNick()));
            }
            model.addAttribute("currentUser", cu);
            model.addAttribute("user_state", us);
            return "admin-panel";
        } else if (us.isLogged() && !us.isAdmin()) {
            return "redirect:/timeline";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("admin-panel/save/json")
    @ResponseBody
    public ResponseEntity<Resource> downloadJson(final Model model) {
        if (us.isLogged() && us.isAdmin()) {
            List<Message> messageList = mm.findAll();
            List<User> userList = um.findAll();
            StringBuilder output = new StringBuilder("");
            try {
                for (Message m : messageList) {
                    String xmlObject = m.serializeToJson() + "\n";
                    output.append(xmlObject);
                }
                for (User u : userList) {
                    String xmlObject = u.serializeToJson() + "\n";
                    output.append(xmlObject);
                }
                Resource file = new ByteArrayResource(output.toString().getBytes());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Database.json\"")
                        .body(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping("admin-panel/save/xml")
    @ResponseBody
    public ResponseEntity<Resource> downloadXML(final Model model) {
        if (us.isLogged() && us.isAdmin()) {
            List<Message> messageList = mm.findAll();
            List<User> userList = um.findAll();
            StringBuilder output = new StringBuilder("");
            try {
                for (Message m : messageList) {
                    String xmlObject = m.serializeToXML() + "\n";
                    output.append(xmlObject);
                }
                for (User u : userList) {
                    String xmlObject = u.serializeToXML() + "\n";
                    output.append(xmlObject);
                }
                Resource file = new ByteArrayResource(output.toString().getBytes());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Database.xml\"")
                        .body(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
