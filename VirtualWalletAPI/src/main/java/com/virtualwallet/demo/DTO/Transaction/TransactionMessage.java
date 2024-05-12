package com.virtualwallet.demo.DTO.Transaction;

import com.virtualwallet.demo.Model.User.User;

public record TransactionMessage(
    TransactionRequestDTO transactionRequestDTO,
    String userInputId
)
{
}
