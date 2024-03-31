package com.virtualwallet.demo.Mappers.User;

import com.virtualwallet.demo.DTO.User.CryptoAddressResponseDTO;
import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.DTO.User.UserResponseDTOBuilder;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserMapper implements IUserMapper
{
    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDTO userToUserResponseDTO(User user)
    {
        Map<CryptoType, CryptoAddressResponseDTO> addresses = user.getCryptosAddress().entrySet().stream()
                .collect(
                        Collectors.toMap(
                            Map.Entry::getKey, e -> cryptoAddressToCryptoAddressResponseDTO(e.getValue())
                        )
                );
        return new UserResponseDTOBuilder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .cryptosAddress((HashMap<CryptoType, CryptoAddressResponseDTO>) addresses)
                .build();
    }

    @Override
    public CryptoAddressResponseDTO cryptoAddressToCryptoAddressResponseDTO(CryptoAddress cryptoAddress)
    {
        return new CryptoAddressResponseDTO(
                cryptoAddress.getAddress(),
                cryptoAddress.getQuantity()
        );
    }

}
