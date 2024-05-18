package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.dao.UserDao;
import com.narvatov.mnews.model.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;

    public Optional<User> findByEmail(String email) {
        return dao.findByEmail(email);
    }

    public void save(User user) {
        dao.save(user);
    }
}
