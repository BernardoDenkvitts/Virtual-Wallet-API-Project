package com.virtualwallet.demo.DTO.User;

import com.virtualwallet.demo.Model.CryptoType;

import java.util.HashMap;

public class UserResponseDTOBuilder
{
    private final UserResponseDTO userResponseDTO;

    public UserResponseDTOBuilder()
    {
        this.userResponseDTO = new UserResponseDTO();
    }

    public UserResponseDTOBuilder id(String id)
    {
        userResponseDTO.setId(id);
        return this;
    }

    public UserResponseDTOBuilder name(String name)
    {
        userResponseDTO.setName(name);
        return this;
    }

    public UserResponseDTOBuilder email(String email)
    {
        userResponseDTO.setEmail(email);
        return this;
    }

    public UserResponseDTOBuilder cryptosAddress(HashMap<CryptoType, CryptoAddressResponseDTO> cryptoAddress)
    {
        userResponseDTO.setCryptoAddresse(cryptoAddress);
        return this;
    }

    public UserResponseDTO build()
    {
        return new UserResponseDTO(
            userResponseDTO.getId(),
            userResponseDTO.getName(),
            userResponseDTO.getEmail(),
            userResponseDTO.getCryptoAddress()
        );
    }


}
