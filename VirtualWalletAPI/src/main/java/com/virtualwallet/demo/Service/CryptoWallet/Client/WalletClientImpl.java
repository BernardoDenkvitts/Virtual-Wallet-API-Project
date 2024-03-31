package com.virtualwallet.demo.Service.CryptoWallet.Client;

import com.virtualwallet.demo.DTO.externalApi.newWallet.NewWalletDTO;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "walletClient",
    url = "${external.api.url}",
    value = "walletClient"
)
public interface WalletClientImpl extends IWalletClient {

    @RequestMapping
    (
        method = RequestMethod.POST,
        value = "/{cryptoType}/main/wallets?token=" + "${token}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    NewWalletDTO createCryptoWallet
    (
        @RequestBody NewWalletDTO newWalletDTO,
        @PathVariable("cryptoType") String cryptoType
    );

    @RequestMapping
    (
        method = RequestMethod.POST,
        value =  "/{cryptoType}" + "/main/wallets/{addressName}/addresses/generate?token=" + "${token}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    CryptoAddress createAddressToCryptoWallet
    (
        @PathVariable("cryptoType") String cryptoType,
        @PathVariable("addressName") String addressName
    );

}
