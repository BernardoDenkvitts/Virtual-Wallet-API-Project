package com.virtualwallet.demo.Exception;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message){
        super(message);
    }
}
