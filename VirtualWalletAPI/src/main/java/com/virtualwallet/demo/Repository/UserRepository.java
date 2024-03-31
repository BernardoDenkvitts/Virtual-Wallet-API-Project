package com.virtualwallet.demo.Repository;

import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    UserDetails findByEmail(String email);
    @Query(value = "{\"cryptosAddress.?0.address\": \"?1\"}")
    User findByCryptoTypeAndAddress(String cryptoType, String address);
}
