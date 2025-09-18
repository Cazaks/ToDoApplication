package com.todoApplication.servises;

import com.todoApplication.datas.models.User;
import com.todoApplication.datas.repositories.UserRepository;
import com.todoApplication.dtos.requests.LoginRequest;
import com.todoApplication.dtos.requests.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

//    private UserRepository userRepository;
    private final UserRepository userRepository;
//    @Autowired
//    public UserServiceImplementation(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email already exists");
        }

        if(registrationRequest.getPassword() == null || registrationRequest.getPassword().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty");
        }
        String hashPassword = BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt());
        registrationRequest.setPassword(hashPassword);

        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }


    @Override
    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guy, you no dey, why you de stress person?");
        }

        User user = optionalUser.get();

        if(!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try de get sense, this password no correct");
        }

        return user;
    }

        @Override
        public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
        }

}