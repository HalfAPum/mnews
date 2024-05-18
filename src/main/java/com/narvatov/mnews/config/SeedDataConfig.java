package com.narvatov.mnews.config;

import com.narvatov.mnews.dao.UserDao;
import com.narvatov.mnews.model.auth.Role;
import com.narvatov.mnews.model.auth.User;
import com.narvatov.mnews.service.auth.JwtService;
import com.narvatov.mnews.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SeedDataConfig implements CommandLineRunner {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) {
        if (userDao.count() > 2) return;

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
