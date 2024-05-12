package com.virtualwallet.demo.Repository;

import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.Model.Transaction;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>
{
    Page<TransactionResponseDTO> getTransactionByInputAddress(String inputAddress, Pageable pageable);
}
