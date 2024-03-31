package com.virtualwallet.demo.Mappers.Authentication;


import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;
import com.virtualwallet.demo.Model.User.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationMapper implements IAuthenticatorMapper
{
    private final ModelMapper modelMapper;

    public AuthenticationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User newAccountRequestDtoToUser(NewAccountRequestDTO dto)
    {
        User user = modelMapper.map(dto, User.class);
        return user;
    }

}
