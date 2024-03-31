package com.virtualwallet.demo.DTO.User;

import java.lang.Double;

public class CryptoAddressResponseDTO
{
    private String address;
    private Double quantity;

    public CryptoAddressResponseDTO() {
    }

    public CryptoAddressResponseDTO(String address, Double quantity) {
        this.address = address;
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void getQuantity(Double quantity) {
        this.quantity = quantity;
    }

}
