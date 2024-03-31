package com.virtualwallet.demo.DTO.Transaction;

import com.virtualwallet.demo.Model.CryptoType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class TransactionRequestDTO
{
    // Sender
    private String inputAddress;
    // Receiver
    private String outputAddress;
    private Double quantity;
    @Enumerated(EnumType.STRING)
    private CryptoType cryptoType;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(String inputAddress, String outputAddress, Double quantity, CryptoType cryptoType) {
        this.inputAddress = inputAddress;
        this.outputAddress = outputAddress;
        this.quantity = quantity;
        this.cryptoType = cryptoType;
    }

    public String getInputAddress() {
        return inputAddress;
    }

    public void setInputAddress(String inputAddress) {
        this.inputAddress = inputAddress;
    }

    public String getOutputAddress() {
        return outputAddress;
    }

    public void setOutputAddress(String outputAddress) {
        this.outputAddress = outputAddress;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public CryptoType getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(CryptoType cryptoType) {
        this.cryptoType = cryptoType;
    }
}
