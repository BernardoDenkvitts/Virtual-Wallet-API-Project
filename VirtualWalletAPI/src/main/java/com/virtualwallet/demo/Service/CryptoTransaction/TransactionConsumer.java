package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionMessage;
import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.Mappers.Transaction.ITransactionMapper;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Repository.TransactionRepository;
import com.virtualwallet.demo.Repository.UserRepository;
import com.virtualwallet.demo.Service.Email.EmailProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

@Service
public class TransactionConsumer
{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConsumer.class);
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ITransactionMapper transactionMapper;

    private final EmailProducer emailProducer;

    public TransactionConsumer(TransactionRepository transactionRepository, ITransactionMapper transactionMapper, UserRepository userRepository, EmailProducer emailProducer) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
        this.emailProducer = emailProducer;
    }

    @KafkaListener(topics = "new-transaction", groupId = "new-crypto-transaction", containerFactory = "jsonKafkaListenerContainerFactory")
    public void receiveTransactionRequest(TransactionMessage message) {
        LOGGER.info("transaction received user {}", message.userInputId());
        LOGGER.info("Transaction information {}; {}; {}", message.transactionRequestDTO().getCryptoType(), message.transactionRequestDTO().getQuantity(), message.transactionRequestDTO().getOutputAddress());

        List<User> transactionUsers = findTransactionUsers(message.userInputId(), message.transactionRequestDTO());
        User userInput = transactionUsers.get(0);
        User userOutput = transactionUsers.get(1);

        updateUserCryptoQuantity(
                userInput,
                message.transactionRequestDTO().getCryptoType(),
                message.transactionRequestDTO().getQuantity(),
                false
        );

        ArrayList<User> usersToSave = new ArrayList<>();
        usersToSave.add(userInput);

        if (userOutput != null) {
            updateUserCryptoQuantity(
                    userOutput,
                    message.transactionRequestDTO().getCryptoType(),
                    message.transactionRequestDTO().getQuantity(),
                    true
            );
            usersToSave.add(userOutput);
        }

        transactionRepository.save(
                transactionMapper.transactionRequestDTOToTransaction(message.transactionRequestDTO())
        );
        userRepository.saveAll(usersToSave);

        String emailMessage = String.format("Your %f %s transaction was completed successfully", message.transactionRequestDTO().getQuantity(), message.transactionRequestDTO().getCryptoType());
        emailProducer.sendEmail(userInput.getEmail(), "Crypto Transaction Completed", emailMessage);

        LOGGER.info(
                "transaction from {} to {} processed",
                message.transactionRequestDTO().getInputAddress(), message.transactionRequestDTO().getOutputAddress()
        );
    }

    private List<User> findTransactionUsers(String userInputID, TransactionRequestDTO transactionRequestDTO)
    {
        User userInput = userRepository.findById(userInputID).orElseThrow(() -> new RuntimeException("Internal Server Error"));

        User userOutput = userRepository.findByCryptoTypeAndAddress(
                transactionRequestDTO.getCryptoType().name(),
                transactionRequestDTO.getOutputAddress());

        return Arrays.asList(userInput, userOutput);
    }


    private void updateUserCryptoQuantity(User user, CryptoType cryptoType, Double cryptoQuantity, boolean increase)
    {
        CryptoAddress userCryptoAddress = user.getCryptosAddress().get(cryptoType);
        double newQuantity = increase ? userCryptoAddress.getQuantity() + cryptoQuantity : userCryptoAddress.getQuantity() - cryptoQuantity;
        userCryptoAddress.setQuantity(newQuantity);
        user.getCryptosAddress().put(cryptoType, userCryptoAddress);
    }
}
