package com.example.springProjekt2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@XmlRootElement(name = "User")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @XmlAttribute(name = "id")
    long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Z a-z]{2,64}$", message = "Too long or contains illegal chars.")
    @XmlAttribute(name = "firstName")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "^[A-Z a-z]{2,64}$", message = "Too long or contains illegal chars.")
    @XmlAttribute(name = "lastName")
    private String lastName;

    @NotEmpty
    @Column(unique = true)
    @Size(min = 8, max = 24, message = "Invalid length.")
    @XmlAttribute(name = "nick")
    private String nick;

    @NotEmpty
    @Size(min = 8, max = 24, message = "Invalid length.")
    @XmlAttribute(name = "password")
    private String password;

    @NotEmpty
    @Column(unique = true)
    @Email(message = "Invalid email.")
    @XmlAttribute(name = "email")
    private String email;

    @ManyToMany(mappedBy = "userList")
    @JsonIgnore
    private List<Message> messageList;

    public String getNick() {
        return nick;
    }

    @Override
    public String toString() {
        return nick;
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
