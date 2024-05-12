package com.virtualwallet.demo.Service.User;

import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.Model.CryptoType;

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

    /**
     * Add crypto quantity to crypto address
     * The purpose of this method is to make possible to play with the API
     *
     * @param userId user id
     * @param cryptoType crypto address type
     * @param quantity quantity to add to crypto address
     * @return simple message telling the crypto quantity was added (String)
     */
    public String addCryptoToAddress(String userId, CryptoType cryptoType, Double quantity);
}
