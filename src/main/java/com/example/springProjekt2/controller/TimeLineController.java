package com.example.springProjekt2.controller;

import com.example.springProjekt2.domain.CurrentUser;
import com.example.springProjekt2.domain.Message;
import com.example.springProjekt2.domain.UserState;
import com.example.springProjekt2.service.MessageManager;
import com.example.springProjekt2.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class TimeLineController {

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


    @GetMapping("timeline")
    public String timeline(@RequestParam(required = false, name = "msearch") String msearch, final Model model) {
        if (us.isLogged()) {
            System.out.println(msearch);
            model.addAttribute("newmsg", new Message());
            List<Message> messageList;
            if (msearch == null) {
                messageList = mm.findAll();
            } else {
                model.addAttribute("msearch", msearch);
                messageList = mm.findByContentContaining(msearch);
            }

            Collections.reverse(messageList);
            model.addAttribute("msgs", messageList);
            model.addAttribute("user_state", us);
            model.addAttribute("current_user", cu);
            return "timeline";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "timeline", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public String newMessage(final Model model, @Valid final Message message, Errors errors, @RequestParam(name = "image", required = false) MultipartFile file) {
        model.addAttribute("newmsg", new Message());
        if (!errors.hasErrors()) {
            message.setUserList(Arrays.asList(cu.getCurrentUser()));

            if (file.isEmpty()) {
                message.setHasImage(false);
            } else {
                message.setHasImage(true);
                message.setAttachments("message" + message.getId() + ".png");
            }
            System.out.println("--------------------------------------------");
            System.out.println(file);
            mm.save(message);
            if (file != null) {
                System.out.println(file);
                try {
                    final String imagePath = "C:/uploaded/";
                    FileOutputStream output = new FileOutputStream(imagePath + message.getId() + ".png");
                    output.write(file.getBytes());
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            List<Message> messageList = mm.findAll();
            Collections.reverse(messageList);
            model.addAttribute("msgs", messageList);
            System.out.println(message.getUserList());
            return "redirect:/timeline";
        }
        List<Message> messageList = mm.findAll();
        Collections.reverse(messageList);
        model.addAttribute("msgs", messageList);
        return "redirect:/timeline";
    }

    @GetMapping("timeline/save/{id}")
    @ResponseBody
    public ResponseEntity<Resource> downloadMessage(@PathVariable("id") long id, final Model model) {
        Message m = mm.findById(id);
        String r = mm.findById(id).toString();
        Resource file = new ByteArrayResource(r.getBytes());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"message" + id + ".txt\"")
                .body(file);
    }

    @GetMapping("timeline/logout")
    public String logout() {
        us.setAdmin(false);
        us.setLogged(false);
        cu.setCurrentUser(null);
        return "redirect:/";
    }

}
