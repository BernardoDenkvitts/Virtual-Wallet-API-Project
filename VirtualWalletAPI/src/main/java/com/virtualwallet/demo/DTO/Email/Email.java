package com.virtualwallet.demo.DTO.Email;

public record Email (
    String to,
    String subject,
    String message
){}
