package com.narvatov.mnews.controller;

import com.narvatov.mnews.dto.request.SignIn;
import com.narvatov.mnews.dto.request.SignUp;
import com.narvatov.mnews.dto.response.JwtAuthenticationResponse;
import com.narvatov.mnews.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public JwtAuthenticationResponse signUp(@RequestBody SignUp signUp) {
        return authenticationService.signUp(signUp);
    }

    @PostMapping("signin")
    public JwtAuthenticationResponse signIn(@RequestBody SignIn signIn) {
        return authenticationService.signIn(signIn);
    }

}
