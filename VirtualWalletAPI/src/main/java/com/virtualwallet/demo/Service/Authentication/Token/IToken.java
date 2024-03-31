package com.virtualwallet.demo.Service.Authentication.Token;

import com.virtualwallet.demo.Model.User.User;

import java.time.Instant;

public interface IToken
{
    /**
     * Generate jwt token for user
     *
     * @param user user object for which the token will be generated
     * @return the token generated (String)
     */
    String generateToken(User user);

    /**
     * Determine how long the token will be valid
     *
     * @return instant time
     */
    Instant tokenExpireHour();

    /**
     * Validate the token
     *
     * @param token token to be validated
     * @return token subject (String)
     */
    String validateToken(String token);

}
