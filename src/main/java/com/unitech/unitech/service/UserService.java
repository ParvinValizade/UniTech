package com.unitech.unitech.service;

import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.exception.UserAlreadyExistException;
import com.unitech.unitech.model.User;
import com.unitech.unitech.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;
  private final ModelMapper modelMapper;


    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
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

        return modelMapper.map(repository.save(user),UserDto.class);
    }

    private void checkUserAlreadyExistOrNot(String pin){
        Optional<User> user = repository.findByPin(pin);
        user.ifPresent(user1 -> {throw new UserAlreadyExistException("User Already Exist");});
    }
}
