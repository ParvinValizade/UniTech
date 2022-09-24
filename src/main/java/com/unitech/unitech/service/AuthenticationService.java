package com.unitech.unitech.service;

import com.unitech.unitech.dto.request.SignInRequest;
import com.unitech.unitech.security.UserPrincipal;
import com.unitech.unitech.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public String singInAndReturnJWT(SignInRequest signInRequest){
        userService.checkPinIsValidOrNot(signInRequest.getPin());
        userService.checkPasswordIsValidOrNot(signInRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getPin(),signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return jwtProvider.generateToken(userPrincipal);
    }


}
