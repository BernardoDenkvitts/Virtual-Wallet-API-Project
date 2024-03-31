package com.virtualwallet.demo.Exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message)
    {
        super(message);
    }
}
