//package com.todoApplication.utils;
//
//import com.todoApplication.datas.models.User;
//import com.todoApplication.dtos.requests.RegistrationRequest;
//import com.todoApplication.dtos.responses.LoginResponse;
//import com.todoApplication.dtos.responses.RegistrationResponse;
//
//public class Mapper {
//
//    public static User toUser(RegistrationRequest registrationRequest) {
//        User registrationResponse = new User();
//        registrationResponse.setFirstName(registrationRequest.getFirstName());
//        registrationResponse.setLastName(registrationRequest.getLastName());
//        registrationResponse.setEmail(registrationRequest.getEmail());
//        registrationResponse.setUserName(registrationRequest.getUserName());
//        registrationResponse.setPassword(registrationRequest.getPassword());
//        return registrationResponse;
//    }
//
//    public static RegistrationResponse toResponse(User user) {
//        return new RegistrationResponse(
//        user.getId(),
//        user.getFirstName(),
//        user.getLastName(),
//        user.getEmail(),
//        user.getPassword()
//        );
//
//    }
//
//    public static User toUser(LoginResponse loginResponse) {
//        User user = new User();
//        user.setId(loginResponse.getId());
//        user.setFirstName(loginResponse.getFirstName());
//        user.setLastName(loginResponse.getLastName());
//    }
//}
