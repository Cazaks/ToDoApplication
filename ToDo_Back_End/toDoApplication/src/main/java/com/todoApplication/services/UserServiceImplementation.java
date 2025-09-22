package com.todoApplication.services;

import com.todoApplication.datas.models.User;
import com.todoApplication.datas.repositories.UserRepository;
import com.todoApplication.dtos.requests.LoginRequest;
import com.todoApplication.dtos.requests.RegistrationRequest;
import com.todoApplication.dtos.responses.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Why you wan tiff persons email na?");
        }

        if(registrationRequest.getEmail() == null || registrationRequest.getEmail().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You no get email?");
        }

        if(!registrationRequest.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Na so them de write email for your village?");
        }


        if(registrationRequest.getFirstName() == null || registrationRequest.getFirstName().trim().isBlank()
        || registrationRequest.getLastName() == null || registrationRequest.getLastName().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your paper and mama bin no give you name wen dem born you?");
        }

        if(registrationRequest.getPassword() == null || registrationRequest.getPassword().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You no carry key how you want take enter, you be tiff?");
        }
        String hashPassword = BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt());
        registrationRequest.setPassword(hashPassword);

        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(hashPassword);
         userRepository.save(user);

         RegistrationResponse registrationResponse = new RegistrationResponse();
         registrationResponse.setId(user.getId());
         registrationResponse.setEmail(user.getEmail());
         registrationResponse.setFullName(user.getFirstName());
         registrationResponse.setMessage("You don try, come inside jour");

         return userRepository.save(user);
    }

        @Override
        public User findByEmail(String email){
            return null;
        }

//
//    @Override
//    public User login(LoginRequest loginRequest) {
//        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
//
//        if(optionalUser.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guy, you no dey, why you de stress person?");
//        }
//
//        User user = optionalUser.get();
//
//        if(!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try de get sense, this password no correct");
//        }
//
//        return user;
//    }


//        return userRepository.findByEmail(email);


}