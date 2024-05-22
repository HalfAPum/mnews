package com.narvatov.mnews.config.auth;

import com.narvatov.mnews.dao.UserDao;
import com.narvatov.mnews.model.user.Role;
import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        if (userDao.count() > 0) return;

        User admin = User.builder()
            .firstName("admin")
            .lastName("admin")
            .email("admin@admin.com")
            .password(passwordEncoder.encode("password"))
            .role(Role.ROLE_ADMIN)
            .build();

        userService.save(admin);
        log.debug("created ADMIN user - {}", admin);
    }

}
