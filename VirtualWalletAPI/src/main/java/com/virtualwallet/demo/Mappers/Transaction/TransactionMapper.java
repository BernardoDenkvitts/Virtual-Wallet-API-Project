package com.virtualwallet.demo.Mappers.Transaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class TransactionMapper implements ITransactionMapper
{
    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction transactionRequestDTOToTransaction(TransactionRequestDTO dto)
    {
        Transaction transaction = modelMapper.map(dto, Transaction.class);
        return transaction;
    }

    public TransactionResponseDTO transactionToTransactionResponseDTO(Transaction transaction)
    {
        TransactionResponseDTO response = modelMapper.map(transaction, TransactionResponseDTO.class);
        return response;
    }
}
