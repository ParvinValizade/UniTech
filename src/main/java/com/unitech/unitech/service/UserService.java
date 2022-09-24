package com.unitech.unitech.service;

import com.unitech.unitech.dto.CityDto;
import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.converter.UserToUserDtoConverter;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.exception.PasswordDoesNotValidException;
import com.unitech.unitech.exception.PinDoesNotValidException;
import com.unitech.unitech.exception.UserAlreadyExistException;
import com.unitech.unitech.exception.UserNotFoundException;
import com.unitech.unitech.model.City;
import com.unitech.unitech.model.User;
import com.unitech.unitech.repository.UserRepository;
import com.unitech.unitech.validation.PasswordValidator;
import com.unitech.unitech.validation.PinValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;
  private final UserToUserDtoConverter converter;
  private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, UserToUserDtoConverter converter, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(CreateUserRequest request, CityDto city){
        checkPinIsValidOrNot(request.getPin());
        checkUserAlreadyExistOrNot(request.getPin());
        checkPasswordIsValidOrNot(request.getPassword());
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate(),
                City.valueOf(city.name()),
                request.getAddress(),
                request.getPin(),
                passwordEncoder.encode(request.getPassword()),
                LocalDateTime.now()
        );

        return converter.convert(repository.save(user));
    }

    public UserDto findUser(String pin){
        return converter.convert(findUserByPin(pin));
    }

    public List<UserDto> findAllUsers(){
        return converter.convert(repository.findAll());
    }

    public User findUserByPin(String pin){
        return repository.findByPin(pin)
                .orElseThrow(()->new UserNotFoundException("User couldn't be found by following pin: "+pin));
    }

    private void checkUserAlreadyExistOrNot(String pin){
        Optional<User> user = repository.findByPin(pin);
        user.ifPresent(user1 -> {throw new UserAlreadyExistException("User Already Exist");});
    }

    protected void checkPasswordIsValidOrNot(String password){
        if (!PasswordValidator.isValid(password)){
            throw new PasswordDoesNotValidException("Password must contain at least one digit [0-9]," +
                    "one lowercase-[a-z] and uppercase Latin character-[A-Z]," +
                    "one special character like ! @ # & ( ) " +
                    "and length of at least 8 characters and a maximum of 20 characters.");
        }
    }

    protected void checkPinIsValidOrNot(String pin){
        if (!PinValidator.isValid(pin)){
            throw new PinDoesNotValidException("Pin must be 7 characters long");
        }
    }
}
