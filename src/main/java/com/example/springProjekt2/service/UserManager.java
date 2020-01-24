package com.example.springProjekt2.service;

import com.example.springProjekt2.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserManager extends CrudRepository<User, Long> {
    <S extends User> S save(S user);

    List<User> findAll();

    User findById(long id);

    User findByNick(String search);

    List<User> findAllByNick(String search);

    void delete(User user);

    void deleteById(long id);

}
