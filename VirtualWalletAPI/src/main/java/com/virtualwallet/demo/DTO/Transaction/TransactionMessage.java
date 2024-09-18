package com.virtualwallet.demo.DTO.Transaction;

public record TransactionMessage(
    TransactionRequestDTO transactionRequestDTO,
    String userInputId
)
{
}
