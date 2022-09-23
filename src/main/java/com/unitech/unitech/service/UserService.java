package com.unitech.unitech.service;

import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.converter.UserToUserDtoConverter;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.dto.request.SignInRequest;
import com.unitech.unitech.exception.UserAlreadyExistException;
import com.unitech.unitech.exception.UserNotFoundException;
import com.unitech.unitech.model.User;
import com.unitech.unitech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;
  private final UserToUserDtoConverter converter;

  private final AuthenticationService authenticationService;


    public UserService(UserRepository repository, UserToUserDtoConverter converter, AuthenticationService authenticationService) {
        this.repository = repository;
        this.converter = converter;
        this.authenticationService = authenticationService;
    }

    public UserDto createUser(CreateUserRequest request){
        checkUserAlreadyExistOrNot(request.getPin());
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getPin(),
                request.getPassword(),
                LocalDateTime.now()
        );

        return converter.convert(repository.save(user));
    }

    public String signInUser(SignInRequest request){
        return authenticationService.singInAndReturnJWT(request);
    }

    public UserDto findUser(String pin){
        return converter.convert(findUserByPin(pin));
    }

    private User findUserByPin(String pin){
        return repository.findByPin(pin)
                .orElseThrow(()->new UserNotFoundException("User couldn't be found by following pin: "+pin));
    }

    private void checkUserAlreadyExistOrNot(String pin){
        Optional<User> user = repository.findByPin(pin);
        user.ifPresent(user1 -> {throw new UserAlreadyExistException("User Already Exist");});
    }
}
