package com.virtualwallet.demo.Model.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.Double;

public class CryptoAddress
{
    @JsonProperty("private")
    private String privateKey;
    @JsonProperty("public")
    private String publicKey;
    private String address;
    private Double quantity = 0.0;

    public CryptoAddress() {
    }

    public CryptoAddress(String privateKey, String publicKey, String address)
    {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

}

