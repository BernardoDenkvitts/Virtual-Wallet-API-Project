package com.virtualwallet.demo.DTO.Transaction;


import com.virtualwallet.demo.Model.CryptoType;

public class AddCryptoToAddress
{
    private String userId;
    private CryptoType cryptoType;
    private Double quantity;

    public AddCryptoToAddress(String userId, CryptoType cryptoType, Double quantity) {
        this.userId = userId;
        this.cryptoType = cryptoType;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CryptoType getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(CryptoType cryptoType) {
        this.cryptoType = cryptoType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
