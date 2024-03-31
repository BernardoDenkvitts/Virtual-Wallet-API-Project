package com.virtualwallet.demo.Service.User;

import com.virtualwallet.demo.DTO.User.UserResponseDTO;

import java.security.Principal;

public interface IUser
{
    /**
     * Get the user information
     *
     * @param userId user id
     * @param principal used for security
     * @return user basic information (UserResponseDTO)
     */
    UserResponseDTO getUserInformation(String userId, Principal principal);
}
