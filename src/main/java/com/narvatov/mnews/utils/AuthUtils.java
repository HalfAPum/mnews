package com.narvatov.mnews.utils;

public class AuthUtils {

    public static final String AUTH_HEADER = "Authorization";


    public static String getJwt(String authHeader) {
        return authHeader.substring(7);
    }

}
