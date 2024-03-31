package com.virtualwallet.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Double;
import java.time.LocalDateTime;

@Document("transaction")
public class Transaction
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String inputAddress;
    private String outputAddress;
    private Double quantity;
    private CryptoType cryptoType;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public Transaction() {
    }

    public Transaction(String id, String inputAddress, String outputAddress, Double quantity, CryptoType cryptoType) {
        this.id = id;
        this.inputAddress = inputAddress;
        this.outputAddress = outputAddress;
        this.quantity = quantity;
        this.cryptoType = cryptoType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", inputAddress='" + inputAddress + '\'' +
                ", outputAddress='" + outputAddress + '\'' +
                ", quantity=" + quantity +
                ", cryptoType=" + cryptoType +
                ", timestamp=" + timestamp +
                '}';
    }
}