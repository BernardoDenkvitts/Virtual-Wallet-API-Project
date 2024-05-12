package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.Model.User.User;

public interface ITransactionProducer {

    /**
     * Send new transaction to Kafka Topic
     *
     * @param transactionRequestDTO object containing data to create a transaction
     * @param userInputId User sending crypto to another address
     */
    void sendTransaction(TransactionRequestDTO transactionRequestDTO, String userInputId);
}
