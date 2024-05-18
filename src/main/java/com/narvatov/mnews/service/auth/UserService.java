package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.dao.UserDao;
import com.narvatov.mnews.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    public Optional<User> findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public void save(User user) {
        dao.save(user);
    }
}
