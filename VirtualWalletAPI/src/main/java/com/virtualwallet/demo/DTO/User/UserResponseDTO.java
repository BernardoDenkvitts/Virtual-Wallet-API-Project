package com.virtualwallet.demo.DTO.User;

import com.virtualwallet.demo.Model.CryptoType;

import java.util.HashMap;

public class UserResponseDTO
{
    private String id;
    private String name;
    private String email;
    private HashMap<CryptoType, CryptoAddressResponseDTO> cryptoAddress;

    protected UserResponseDTO(String id, String name, String email, HashMap<CryptoType, CryptoAddressResponseDTO> cryptoAddress)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cryptoAddress = cryptoAddress;
    }

    public UserResponseDTO() {
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    public HashMap<CryptoType, CryptoAddressResponseDTO> getCryptoAddress(){
        return cryptoAddress;
    }

    protected void setCryptoAddresse(HashMap<CryptoType, CryptoAddressResponseDTO> cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

}
