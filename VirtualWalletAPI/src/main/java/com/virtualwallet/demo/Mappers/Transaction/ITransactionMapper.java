package com.virtualwallet.demo.Mappers.Transaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.Transaction;

public interface ITransactionMapper {
    Transaction transactionRequestDTOToTransaction(TransactionRequestDTO dto);
    TransactionResponseDTO transactionRequestDTOToTransactionResponseDTO(TransactionRequestDTO dto);
}
