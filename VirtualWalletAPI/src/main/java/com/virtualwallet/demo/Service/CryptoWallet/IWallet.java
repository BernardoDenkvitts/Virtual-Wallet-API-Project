package com.virtualwallet.demo.Service.CryptoWallet;

import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.CryptoAddress;

import java.util.HashMap;

public interface IWallet
{
    /**
     * Create a wallet for each type of crypto (just bcy at moment)
     *
     * @return HashMap containing all crypto addresses,
     * representing a new wallet (HashMap<CryptoType, CryptoAddress>)
     */
    HashMap<CryptoType, CryptoAddress> createWallet();
}
