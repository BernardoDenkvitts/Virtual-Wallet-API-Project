package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.CryptoType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface ITransaction
{
    /**
     * Send crypto to specific address
     *
     * @param transactionRequestDTO object containing necessary information to send crypto
     * @param userId id of the user sending crypto
     * @param principal used for security
     * @return new transaction created (TransactionResponseDTO)
     */
    TransactionResponseDTO sendCryptoToAddress(TransactionRequestDTO transactionRequestDTO, String userId, Principal principal);

    /**
     * Retrieve user transactions for a specific crypto type
     *
     * @param userId user id
     * @param cryptoType crypto type
     * @param pageable controls how the result are retrieved and organized
     * @param principal used for security
     * @return page of user transactions (Page<TransactionResponseDTO>)
     */
    Page<TransactionResponseDTO> getUserTransactionsByCryptoType(String userId, CryptoType cryptoType, Pageable pageable, Principal principal);
}
