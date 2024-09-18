package com.virtualwallet.demo.Service.Email;

import com.virtualwallet.demo.DTO.Email.Email;
import com.virtualwallet.demo.Service.CryptoWallet.CryptoWalletProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EmailProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(String userEmail, String emailMessage){
        LOGGER.info("Sending email to user {} to email-topic", userEmail);
        LOGGER.info("Email message: {}", emailMessage);

        kafkaTemplate.send("email-topic", new Email(userEmail, emailMessage));
        LOGGER.info("Email Sent");
    }

}
