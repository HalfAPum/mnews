package com.narvatov.mnews.service.auth;

import com.narvatov.mnews.dto.request.SignIn;
import com.narvatov.mnews.dto.request.SignUp;
import com.narvatov.mnews.dto.response.JwtAuthenticationResponse;
import com.narvatov.mnews.model.auth.Role;
import com.narvatov.mnews.model.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
