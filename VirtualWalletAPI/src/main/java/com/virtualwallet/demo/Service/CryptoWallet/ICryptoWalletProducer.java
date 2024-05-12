package com.virtualwallet.demo.Service.CryptoWallet;

public interface ICryptoWalletProducer {

    /**
     * Send new wallet request to Kafka Topic
     *
     * @param userId User to create the new wallet
     */
    void sendCryptoWalletRequest(String userId);
}
