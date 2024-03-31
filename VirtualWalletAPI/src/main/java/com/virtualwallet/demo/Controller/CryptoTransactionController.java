package com.virtualwallet.demo.Controller;

import com.virtualwallet.demo.DTO.Transaction.AddCryptoToAddress;
import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Service.CryptoTransaction.ITransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/transaction")
public class CryptoTransactionController
{

    private final ITransaction transactionService;
    public CryptoTransactionController(ITransaction transactionService)
    {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> addCryptoQuantityToAddress(@RequestBody AddCryptoToAddress addCryptoToAddress)
    {
        return ResponseEntity.ok(transactionService.addCryptoToAddress(addCryptoToAddress));
    }

    @PostMapping("/{userId}/send")
    public ResponseEntity<TransactionResponseDTO> sendCryptoToDifferentAddress
    (
        @PathVariable("userId") String userId,
        @RequestBody TransactionRequestDTO transactionRequestDTO,
        Principal principal
    )
    {
        TransactionResponseDTO newTransaction = transactionService.sendCryptoToAddress(transactionRequestDTO, userId, principal);
        return ResponseEntity.accepted().body(newTransaction);

    }

    @GetMapping("/{userId}/{cryptoType}")
    public ResponseEntity<Page<TransactionResponseDTO>> getUserTransactionsByCryptoType
    (
       @PathVariable("userId") String userId,
       @PathVariable("cryptoType") CryptoType cryptoType,
       Pageable pageable,
       Principal principal
    )
    {
        return ResponseEntity.ok(transactionService.getUserTransactionsByCryptoType(userId, cryptoType, pageable, principal));
    }

}
