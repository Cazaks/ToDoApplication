package com.todoApplication.servises;

import com.todoApplication.datas.models.User;
import com.todoApplication.dtos.requests.LoginRequest;
import com.todoApplication.dtos.requests.RegistrationRequest;

import java.util.Optional;

public interface UserService {

    User registerUser(RegistrationRequest registrationRequest);

    User login(LoginRequest loginRequest);

    Optional<User> findByEmail(String email);

}
