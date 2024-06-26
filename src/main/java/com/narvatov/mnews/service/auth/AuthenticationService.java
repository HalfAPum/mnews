package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.dto.request.auth.SignIn;
import com.narvatov.mnews.dto.request.auth.SignUp;
import com.narvatov.mnews.dto.response.JwtAuthenticationResponse;
import com.narvatov.mnews.model.user.Role;
import com.narvatov.mnews.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUp signUp) {
        User user = User.builder()
            .firstName(signUp.getFirstName())
            .lastName(signUp.getLastName())
            .email(signUp.getEmail())
            .password(passwordEncoder.encode(signUp.getPassword()))
            .role(Role.ROLE_USER)
            .build();

        userService.save(user);

        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignIn signIn) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }

        User user = userService.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

}
