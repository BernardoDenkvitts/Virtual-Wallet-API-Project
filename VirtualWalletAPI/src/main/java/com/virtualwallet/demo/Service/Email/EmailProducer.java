package com.virtualwallet.demo.Service.Email;

import com.virtualwallet.demo.DTO.Email.Email;
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

    public void sendEmail(String email, String subject, String message){
        LOGGER.info("Sending email to user {} to email-topic", email);
        LOGGER.info("Email subject: {}", subject);
        LOGGER.info("Email message: {}", message);

        kafkaTemplate.send("email-topic", new Email(email, subject, message));
        LOGGER.info("Message sent to email-topic");
    }

}
