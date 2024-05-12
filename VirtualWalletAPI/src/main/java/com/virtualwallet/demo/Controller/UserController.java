package com.virtualwallet.demo.Controller;

import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Service.User.IUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/user")
public class UserController
{
    private final IUser userService;

    public UserController(IUser userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserData(@PathVariable("userId") String userId, Principal principal)
    {
        UserResponseDTO userResponseDTO = userService.getUserInformation(userId, principal);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/{userId}/add/{cryptoType}/{quantity}")
    public ResponseEntity<String> addCryptoQuantityToAddress(
        @PathVariable("userId") String userId,
        @PathVariable("cryptoType") CryptoType cryptoType,
        @PathVariable("quantity") Double quantity
    )
    {
        return ResponseEntity.ok(userService.addCryptoToAddress(userId, cryptoType, quantity));
    }

}
