package com.virtualwallet.demo.Service.CryptoWallet;

import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


@Service
public class CryptoWalletConsumer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoWalletConsumer.class);
    private final UserRepository userRepository;
    private final IWallet walletClient;

    public CryptoWalletConsumer(UserRepository userRepository, IWallet walletClient)
    {
        this.userRepository = userRepository;
        this.walletClient = walletClient;
    }

    @Transactional
    @KafkaListener(topics = "new-crypto-wallet", groupId = "new-wallet", containerFactory = "stringKafkaListenerContainerFactory")
    public void receiveCryptoWalletRequest(String userId)
    {
        LOGGER.info("Crypto Wallet Request received for user {}", userId);

        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Internal Server Error"));
        HashMap<CryptoType, CryptoAddress> newWallet = walletClient.createWallet();
        user.setCryptosAddress(newWallet);
        userRepository.save(user);

        LOGGER.info("Crypto wallet created for {}", user.getEmail());
    }
}