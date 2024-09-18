package com.virtualwallet.demo.Service.CryptoTransaction;

import com.virtualwallet.demo.DTO.Transaction.TransactionMessage;
import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer implements ITransactionProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, Object> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(TransactionRequestDTO transactionRequestDTO, String userInputId) {
        TransactionMessage message = new TransactionMessage(transactionRequestDTO, userInputId);
        kafkaTemplate.send("new-transaction", message);
    }
}
