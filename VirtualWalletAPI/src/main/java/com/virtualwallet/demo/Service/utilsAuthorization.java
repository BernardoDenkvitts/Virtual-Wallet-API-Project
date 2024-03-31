package com.virtualwallet.demo.Service;

import com.virtualwallet.demo.Exception.NotFoundException;

import java.security.Principal;

public class utilsAuthorization
{
    private static final String UNAUTHORIZED = "Not Found";

    /**
     * Verify if the user sending the request is the endpoint owner
     * @param username endpoint owner username
     * @param principal requester information
     * @return
     */
    public static boolean verifyIdentity(String username, Principal principal)
    {
        if (!username.equals(principal.getName()))
        {
            throw new NotFoundException(UNAUTHORIZED);
        }
        return true;
    }
}
