package com.virtualwallet.demo.Service.User;

import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.Exception.NotFoundException;
import com.virtualwallet.demo.Mappers.User.IUserMapper;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Repository.UserRepository;
import com.virtualwallet.demo.Service.utilsAuthorization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class UserService implements IUser
{
    private final UserRepository repository;
    private final IUserMapper userMapper;

    public UserService(UserRepository repository, IUserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO getUserInformation(String userId, Principal principal)
    {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("Not found"));
        utilsAuthorization.verifyIdentity(user.getUsername(), principal);
        return userMapper.userToUserResponseDTO(user);
    }

    @Transactional
    public String addCryptoToAddress(String userId, CryptoType cryptoType, Double quantity)
    {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        CryptoAddress cryptoAddress = user.getCryptosAddress().get(cryptoType);
        cryptoAddress.setQuantity(cryptoAddress.getQuantity() + quantity);

        user.getCryptosAddress().put(cryptoType, cryptoAddress);
        repository.save(user);

        return "Quantity added";
    }

}
