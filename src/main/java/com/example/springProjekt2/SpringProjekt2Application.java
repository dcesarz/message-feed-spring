package com.example.springProjekt2;

import com.example.springProjekt2.domain.Message;
import com.example.springProjekt2.domain.User;
import com.example.springProjekt2.service.MessageManager;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringProjekt2Application {

    private UserManager um;
    private MessageManager mm;

    public static void main(String[] args) {
        SpringApplication.run(SpringProjekt2Application.class, args);
    }

    @Autowired
    public void setUm(UserManager um) {
        this.um = um;
    }

    @Autowired
    public void setMm(MessageManager mm) {
        this.mm = mm;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startAction() {
        User user = um.findByNick("administrator");
        Message message = new Message();
        message.setContent("Hello! This is message from admin!");
        List<User> authors = new ArrayList<>();

        if (user == null) {
            user = new User();
            user.setNick("administrator");
            user.setPassword("password");
            user.setEmail("administrator@email.com");
            user.setFirstName("Administrator");
            user.setLastName("Administrator");
            um.save(user);
            System.out.println("Creating administrator profile..");
            authors.add(um.findByNick("administrator"));
            message.setUserList(authors);
            mm.save(message);
            return;
        }

        System.out.println("Administrator exists.");
        authors.add(user);
        message.setUserList(authors);
        mm.save(message);
    }

}
