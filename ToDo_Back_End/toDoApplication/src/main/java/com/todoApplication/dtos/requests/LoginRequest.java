package com.todoApplication.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {

    private String email;
    private String password;


//    @NotBlank(message = "Email is required")
//    @Email(message = "Enter a valid email")
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    private String password;
}
