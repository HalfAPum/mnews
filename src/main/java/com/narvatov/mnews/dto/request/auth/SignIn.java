package com.narvatov.mnews.dto.request.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class SignIn {

    private String email;
    private String password;

}
