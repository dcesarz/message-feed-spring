package com.example.springProjekt2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;
    @ManyToMany
    @JsonIgnore
    List<User> userList;
    @Size(max = 255, message = "Your message is too long.")
    private String content;
    @DateTimeFormat
    private Timestamp create_date;
    private String attachments;
    private Boolean hasImage;

    public Message() {
        this.create_date = new Timestamp((new java.util.Date().getTime()));
    }

    @Override
    public String toString() {
        List<String> nicknames = new ArrayList<>();
        for (User u : userList) {
            nicknames.add(u.getNick());
        }
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", create_date=" + create_date +
                ", attachments='" + attachments +
                ", authors='" + nicknames + "}";
    }

    public String serializeToXML() throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(this);
    }

    public String serializeToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}
