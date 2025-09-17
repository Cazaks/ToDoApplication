package com.todoApplication.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private int id;

    private String email;
    private String userName;
    private String firstName;
    private String lastName;
}
