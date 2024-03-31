package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.AddCryptoToAddress;
import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Exception.InsufficientFundsException;
import com.virtualwallet.demo.Exception.NotFoundException;
import com.virtualwallet.demo.Mappers.Transaction.ITransactionMapper;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.Transaction;
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
import java.util.ArrayList;

@Service
public class TransactionService implements ITransaction
{
    private final ITransactionMapper transactionMapper;
    private final TransactionRepository repository;
    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND = "User Not Found";
    private static final String INSUFFICIENT_FUNDS = "Insufficient Funds";

    public TransactionService(TransactionRepository repository, UserRepository userRepository, ITransactionMapper transactionMapper)
    {
        this.repository = repository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public String addCryptoToAddress(AddCryptoToAddress addCryptoToAddress)
    {
        User user = userRepository.findById(addCryptoToAddress.getUserId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        CryptoAddress cryptoAddress = user.getCryptosAddress().get(addCryptoToAddress.getCryptoType());
        cryptoAddress.setQuantity(cryptoAddress.getQuantity() + addCryptoToAddress.getQuantity());

        user.getCryptosAddress().put(addCryptoToAddress.getCryptoType(), cryptoAddress);
        userRepository.save(user);
        return "Quantity added";
    }

    // If the output address is wrong, so the input user will lose that quantity;
    @Override
    @Transactional
    public TransactionResponseDTO sendCryptoToAddress(
            TransactionRequestDTO transactionRequestDTO, String userId, Principal principal
    )
    {
        User userInput = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        utilsAuthorization.verifyIdentity(userInput.getUsername(), principal);

        CryptoAddress cryptoAddress = userInput.getCryptosAddress().get(transactionRequestDTO.getCryptoType());
        if (cryptoAddress.getQuantity() < transactionRequestDTO.getQuantity()) throw new InsufficientFundsException(INSUFFICIENT_FUNDS);

        cryptoAddress.setQuantity(cryptoAddress.getQuantity() - transactionRequestDTO.getQuantity());
        userInput.getCryptosAddress().put(transactionRequestDTO.getCryptoType(), cryptoAddress);
        ArrayList<User> usersToSave = new ArrayList<>();
        usersToSave.add(userInput);

        User userOutput = userRepository.findByCryptoTypeAndAddress(
            transactionRequestDTO.getCryptoType().name(),
            transactionRequestDTO.getOutputAddress()
        );

        if(userOutput != null)
        {
            CryptoAddress outputAddress = userOutput.getCryptosAddress().get(transactionRequestDTO.getCryptoType());
            outputAddress.setQuantity(outputAddress.getQuantity() + transactionRequestDTO.getQuantity());
            usersToSave.add(userOutput);
        }
        Transaction newTransaction = repository.save(
                transactionMapper.transactionRequestDTOToTransaction(transactionRequestDTO)
        );
        userRepository.saveAll(usersToSave);
        return transactionMapper.transactionToTransactionResponseDTO(newTransaction);
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

        return repository.getTransactionByInputAddress(
                user.getCryptosAddress().get(cryptoType).getAddress(), paging
        );
    }

}
