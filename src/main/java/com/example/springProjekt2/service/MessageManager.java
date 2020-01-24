package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.Message;
import com.example.springProjekt2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageManager extends CrudRepository<Message, Long> {
    <S extends Message> S save(S msg);

    List<Message> findAll();

    Message findById(long id);

    //czy potrzebuje obu ponizszych metod?

    List<Message> findByContent(String content);

    List<Message> findByContentIgnoreCaseContaining(String search);

    //to fix ; we should be able to search for one author.
    //List <Message> findByUserList(List<User> users);

    List<Message> findByContentContaining(String search);

    void delete(Message message);

    void deleteById(long id);

    @Transactional
    void deleteByUserList_Id(long id);

    // same as above
    @Transactional
    void deleteAllByUserList(User user);
}
