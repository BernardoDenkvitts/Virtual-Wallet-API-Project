package com.virtualwallet.demo.Mappers.User;

import com.virtualwallet.demo.DTO.User.CryptoAddressResponseDTO;
import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;

public interface IUserMapper
{
    UserResponseDTO userToUserResponseDTO(User user);

    CryptoAddressResponseDTO cryptoAddressToCryptoAddressResponseDTO(CryptoAddress cryptoAddress);
}
