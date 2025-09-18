package com.todoApplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
