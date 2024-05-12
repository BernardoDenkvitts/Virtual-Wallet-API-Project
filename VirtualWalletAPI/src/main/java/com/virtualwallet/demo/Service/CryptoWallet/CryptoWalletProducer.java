package com.virtualwallet.demo.Service.CryptoWallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CryptoWalletProducer implements ICryptoWalletProducer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoWalletProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CryptoWalletProducer(KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCryptoWalletRequest(String userId)
    {
        LOGGER.info("Sending message for {} to new-crypto-wallet topic", userId);
        kafkaTemplate.send("new-crypto-wallet", userId);
    }

}
