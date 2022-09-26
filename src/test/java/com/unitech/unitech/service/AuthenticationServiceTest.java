package com.unitech.unitech.service;

import com.unitech.unitech.dto.request.SignInRequest;
import com.unitech.unitech.exception.PinDoesNotValidException;
import com.unitech.unitech.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private  UserService userService;


    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp(){
        authenticationManager = mock(AuthenticationManager.class);
        jwtProvider = mock(JwtProvider.class);
        userService = mock(UserService.class);

        authenticationService = new AuthenticationService(authenticationManager,
                jwtProvider,userService);
    }

    @Test
    void testSingInAndReturnJWT_whenPinDoesNotValid_itShouldThrowPinDoesNotValidException(){
        SignInRequest signInRequest = new SignInRequest("123456","123456aA@");

        doThrow(PinDoesNotValidException.class)
                .when(userService).checkPinIsValidOrNot(signInRequest.getPin());

        assertThrows(PinDoesNotValidException.class,()->
                authenticationService.singInAndReturnJWT(signInRequest));

        verify(userService).checkPinIsValidOrNot("123456");
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(authenticationManager);
        verifyNoInteractions(jwtProvider);
    }
}
