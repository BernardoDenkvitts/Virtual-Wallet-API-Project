package com.virtualwallet.demo.Mappers.Transaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class TransactionMapper implements ITransactionMapper
{
    private ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction transactionRequestDTOToTransaction(TransactionRequestDTO dto)
    {
        return modelMapper.map(dto, Transaction.class);
    }

    public TransactionResponseDTO transactionRequestDTOToTransactionResponseDTO(TransactionRequestDTO dto)
    {
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(dto, TransactionResponseDTO.class);
        transactionResponseDTO.setTimestamp(LocalDateTime.now());
        return transactionResponseDTO;
    }
}
