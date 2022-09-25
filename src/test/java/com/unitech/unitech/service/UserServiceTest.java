package com.unitech.unitech.service;

import com.unitech.unitech.TestSupport;
import com.unitech.unitech.dto.CityDto;
import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.converter.UserToUserDtoConverter;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.exception.PasswordDoesNotValidException;
import com.unitech.unitech.exception.PinDoesNotValidException;
import com.unitech.unitech.exception.UserAlreadyExistException;
import com.unitech.unitech.exception.UserNotFoundException;
import com.unitech.unitech.model.User;
import com.unitech.unitech.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

class UserServiceTest extends TestSupport {

    private  UserRepository userRepository;
    private  UserToUserDtoConverter userToUserDtoConverter;
    private  PasswordEncoder passwordEncoder;

    private UserService userService;


    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class);
        userToUserDtoConverter = mock(UserToUserDtoConverter.class);
        passwordEncoder = mock(PasswordEncoder.class);

        userService = new UserService(userRepository,userToUserDtoConverter,passwordEncoder);
    }

    @Test
    void testCreateUser_whenPinAndPasswordIsValidAndUserDoesNotExist_shouldCreateUser(){
        CreateUserRequest request = generateCreateUserRequest();
        CityDto cityDto = CityDto.Baku;
        User user = generateUser();
        UserDto expected = generateUserDto();


        when(userRepository.findByPin(request.getPin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        when(userToUserDtoConverter.convert(user)).thenReturn(expected);

        UserDto result = userService.createUser(request,cityDto);

        assertEquals(expected,result);

        verify(userRepository).save(user);
        verify(userToUserDtoConverter).convert(user);
    }

    @Test
    void testCreateUser_whenPinDoesNotValid_itShouldThrowPinDoesNotValidException(){
        CreateUserRequest request = generateCreateUserRequestWithInvalidPin();

        assertThrows(PinDoesNotValidException.class,()->
                userService.createUser(request,CityDto.Baku));

        verifyNoInteractions(userRepository);
        verifyNoInteractions(userToUserDtoConverter);

    }

    @Test
    void testCreateUser_whenPasswordDoesNotValid_itShouldThrowPasswordDoesNotValidException(){
        CreateUserRequest request = generateCreateUserRequestWithInvalidPassword();

        when(userRepository.findByPin(request.getPin())).thenReturn(Optional.empty());

        assertThrows(PasswordDoesNotValidException.class,()->
                userService.createUser(request,CityDto.Baku));

        verify(userRepository).findByPin(request.getPin());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userToUserDtoConverter);

    }

    @Test
    void testCreateUser_whenPinAndPasswordIsValidButUserAlreadyExist_itShouldTrowUserAlreadyExistException(){
        User user = generateUser();
        CreateUserRequest request = generateCreateUserRequest();

        when(userRepository.findByPin(request.getPin())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class,()->
                userService.createUser(request,CityDto.Baku));

        verify(userRepository).findByPin(request.getPin());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userToUserDtoConverter);

    }

    @Test
    void testFindAllUsers_itShouldReturnUserDtoList(){
        List<User> userList = generateUserList();
        List<UserDto> expectedUserDtoList = generateUserDtoList(userList);

        when(userRepository.findAll()).thenReturn(userList);
        when(userToUserDtoConverter.convert(userList)).thenReturn(expectedUserDtoList);

        List<UserDto> resultList = userService.findAllUsers();

        assertEquals(expectedUserDtoList,resultList);

        verify(userRepository).findAll();
        verify(userToUserDtoConverter).convert(userList);

    }

    @Test
    void testFindUserByPin_whenUserIsExist_itShouldReturnUser(){
        String pin = "12345A7";
        User expectedUser = generateUser();

        when(userRepository.findByPin(pin)).thenReturn(Optional.of(expectedUser));

        User result = userService.findUserByPin(pin);

        assertEquals(expectedUser,result);
        verify(userRepository).findByPin(pin);

    }

    @Test
    void testFindUserByPin_whenUserDoesNotExist_itShouldThrowUserNotFoundException(){
        String pin = "12345A7";

        when(userRepository.findByPin(pin)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->
                userService.findUserByPin(pin));

        verify(userRepository).findByPin(pin);

    }

}
