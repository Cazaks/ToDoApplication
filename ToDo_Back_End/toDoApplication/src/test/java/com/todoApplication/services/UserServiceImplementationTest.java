package com.todoApplication.services;

import com.todoApplication.datas.models.User;
import com.todoApplication.datas.repositories.UserRepository;
import com.todoApplication.dtos.requests.RegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class UserServiceImplementationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Ojim");
        registrationRequest.setLastName("Osinachi");
        registrationRequest.setEmail("ojimosinachi21@gmail.com");
        registrationRequest.setPassword("osinach1443");
    }

    @Test
    void TestThatUser_IsRegistered_Successfully() {
        User savedUser = userService.registerUser(registrationRequest);

        System.out.println("Saved user: " + savedUser);

//        returning a valid user object
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("ojimosinachi21@gmail.com", savedUser.getEmail());

//        verify user is saved
        User foundUser = userRepository.findByEmail("ojimosinachi21@gmail.com").orElse(null);

//        user saved in db
        assertNotNull(foundUser);
        assertEquals("Ojim",  foundUser.getFirstName());

    }

    @Test
    void TestThatFirstAndLastNameThrowExceptionErrorIfEmpty(){
        registrationRequest.setEmail("");
        registrationRequest.setLastName("");

        try {
            userService.registerUser(registrationRequest);
            fail("Registration should fail because first and last name is empty");
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertEquals("Your paper and mama bin no give you name wen dem born you?", ex.getReason());
        }
    }

    @Test
    void TestDuplicateEmail_ThrowsEmailAlreadyExistExceptionError() {
        User savedUser = userService.registerUser(registrationRequest);

        try {
            userService.registerUser(registrationRequest);
            fail("The second registration should fail because email already exist");

        } catch (ResponseStatusException ex){
           assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
           assertEquals("Why you wan tiff persons email na?", ex.getReason());
        }
    }

    @Test
    void TestThatUserRegistration_ThrowsPassWordExceptionError_IfUserPasswordIsEmpty() {
        registrationRequest.setPassword("");
        try {
            userService.registerUser(registrationRequest);
            fail("The registration should fail because password is empty");
        } catch (ResponseStatusException ex){
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertEquals("You no carry key how you want take enter, you be tiff?", ex.getReason());
        }
    }






    @AfterEach
    void tearDown() {
        userRepository.deleteAll();

    }
}