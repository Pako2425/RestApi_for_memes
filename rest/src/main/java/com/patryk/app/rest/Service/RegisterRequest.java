package com.patryk.app.rest.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String name;
    private final String password;
    private final String repeatPassword;
}
