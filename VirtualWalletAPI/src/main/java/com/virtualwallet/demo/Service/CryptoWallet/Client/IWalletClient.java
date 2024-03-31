package com.virtualwallet.demo.Service.CryptoWallet.Client;

import com.virtualwallet.demo.DTO.externalApi.newWallet.NewWalletDTO;
import com.virtualwallet.demo.Model.User.CryptoAddress;

public interface IWalletClient
{
    /**
     * Create new crypto wallet
     *
     * @param newWalletDTO wallet name
     * @param cryptoType crypto type
     * @return new wallet name
     */
    NewWalletDTO createCryptoWallet(
        NewWalletDTO newWalletDTO,
        String cryptoType
    );

    /**
     * Create address to wallet
     *
     * @param cryptoType crypto type
     * @param addressName address name
     * @return a new crypto address (CryptoAddress)
     */
    CryptoAddress createAddressToCryptoWallet(
        String cryptoType,
        String addressName
    );

}
