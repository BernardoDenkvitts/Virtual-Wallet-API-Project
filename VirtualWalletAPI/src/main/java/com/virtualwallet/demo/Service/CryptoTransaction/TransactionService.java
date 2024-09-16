package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Exception.InsufficientFundsException;
import com.virtualwallet.demo.Exception.NotFoundException;
import com.virtualwallet.demo.Mappers.Transaction.ITransactionMapper;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Repository.TransactionRepository;
import com.virtualwallet.demo.Repository.UserRepository;
import com.virtualwallet.demo.Service.utilsAuthorization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class TransactionService implements ITransaction
{
    private final ITransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ITransactionProducer transactionProducer;
    private static final String USER_NOT_FOUND = "User Not Found";
    private static final String INSUFFICIENT_FUNDS = "Insufficient Funds";

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
                              ITransactionMapper transactionMapper, ITransactionProducer transactionProducer
    )
    {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
        this.transactionProducer = transactionProducer;
    }

    // If the output address is wrong, so the input user will lose that quantity;
    @Override
    public TransactionResponseDTO sendCryptoToAddress(
            TransactionRequestDTO transactionRequestDTO, String userId, Principal principal
    )
    {
        User userInput = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        utilsAuthorization.verifyIdentity(userInput.getUsername(), principal);

        CryptoAddress cryptoAddress = userInput.getCryptosAddress().get(transactionRequestDTO.getCryptoType());
        if (cryptoAddress.getQuantity() < transactionRequestDTO.getQuantity()) throw new InsufficientFundsException(INSUFFICIENT_FUNDS);

        transactionProducer.sendTransaction(transactionRequestDTO, userInput.getId());

        return transactionMapper.transactionRequestDTOToTransactionResponseDTO(transactionRequestDTO);
    }

    @Override
    public Page<TransactionResponseDTO> getUserTransactionsByCryptoType(
            String userId, CryptoType cryptoType, Pageable pageable, Principal principal
    )
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        utilsAuthorization.verifyIdentity(user.getUsername(), principal);

        PageRequest paging = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "timestamp"))
        );

        return transactionRepository.getTransactionByInputAddress(
                user.getCryptosAddress().get(cryptoType).getAddress(), paging
        );
    }

}
