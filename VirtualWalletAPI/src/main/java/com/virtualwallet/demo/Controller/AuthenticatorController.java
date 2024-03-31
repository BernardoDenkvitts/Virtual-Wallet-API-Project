package com.virtualwallet.demo.Controller;


import com.virtualwallet.demo.DTO.Authentication.LoginRequestDTO;
import com.virtualwallet.demo.DTO.Authentication.LoginResponseDTO;
import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;
import com.virtualwallet.demo.Service.Authentication.IAuthenticator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticatorController
{
    private final IAuthenticator authentication;

    public AuthenticatorController(IAuthenticator authentication)
    {
        this.authentication = authentication;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody NewAccountRequestDTO newAccountRequestDTO, UriComponentsBuilder ucb)
    {
        String newUserId = authentication.register(newAccountRequestDTO);
        URI newUserLocation = ucb.path("/v1/user/{id}").buildAndExpand(newUserId).toUri();
        return ResponseEntity.created(newUserLocation).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(authentication.login(loginRequestDTO));
    }

}
