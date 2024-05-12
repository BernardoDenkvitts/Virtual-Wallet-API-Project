package com.virtualwallet.demo.Service.CryptoWallet;

import com.virtualwallet.demo.DTO.externalApi.newWallet.NewWalletDTO;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Service.CryptoWallet.Client.IWalletClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class WalletService implements IWallet
{
    private final IWalletClient walletClient;

    public WalletService(IWalletClient walletClient)
    {
        this.walletClient = walletClient;
    }

    public HashMap<CryptoType, CryptoAddress> createWallet()
    {
        HashMap<CryptoType, CryptoAddress> userWallet = new HashMap<>();

        // Create address for each type of crypto
        for(CryptoType type: CryptoType.values())
        {
            String name = createWalletName();
            String cryptoWallet = walletClient.createCryptoWallet(
                    new NewWalletDTO(name),
                    type.name()
            ).name();

            CryptoAddress cryptoAddress = walletClient.createAddressToCryptoWallet(type.name(), cryptoWallet);
            userWallet.put(type, cryptoAddress);
        }
        return userWallet;
    }

    private String createWalletName()
    {
        String name = UUID.randomUUID().toString().substring(0, 20);
        if (name.charAt(0) == '1' || name.charAt(0) == '3'){
            name = 'A' + name.substring(1);
        }
        return name;
    }

}
