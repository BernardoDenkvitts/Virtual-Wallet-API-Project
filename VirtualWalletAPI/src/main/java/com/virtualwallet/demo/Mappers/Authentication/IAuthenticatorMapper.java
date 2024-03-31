package com.virtualwallet.demo.Mappers.Authentication;

import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;
import com.virtualwallet.demo.Model.User.User;

public interface IAuthenticatorMapper
{
    User newAccountRequestDtoToUser(NewAccountRequestDTO dto);

}
