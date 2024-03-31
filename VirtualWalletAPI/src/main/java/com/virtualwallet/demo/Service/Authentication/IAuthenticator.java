package com.virtualwallet.demo.Service.Authentication;

import com.virtualwallet.demo.DTO.Authentication.LoginRequestDTO;
import com.virtualwallet.demo.DTO.Authentication.LoginResponseDTO;
import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;


public interface IAuthenticator
{
    /**
     * Register new user into database
     *
     * @param newAccountRequestDTO DTO containing the new user information
     * @return new user ID (String)
     */
    String register(NewAccountRequestDTO newAccountRequestDTO);

    /**
     * Authenticate existing user
     *
     * @param loginRequestDTO DTO containing user credentials
     * @return login token (LoginResponseDTO)
     */
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
