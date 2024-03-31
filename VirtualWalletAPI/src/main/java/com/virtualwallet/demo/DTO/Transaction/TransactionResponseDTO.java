package com.virtualwallet.demo.DTO.Transaction;

import com.virtualwallet.demo.Model.CryptoType;

import java.time.LocalDateTime;

public class TransactionResponseDTO
{
    private String inputAddress;
    private String outputAddress;
    private Double quantity;
    private CryptoType cryptoType;
    private LocalDateTime timestamp;

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(String inputAddress, String outputAddress, Double quantity, CryptoType cryptoType, LocalDateTime timestamp) {
        this.inputAddress = inputAddress;
        this.outputAddress = outputAddress;
        this.quantity = quantity;
        this.cryptoType = cryptoType;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }
}
