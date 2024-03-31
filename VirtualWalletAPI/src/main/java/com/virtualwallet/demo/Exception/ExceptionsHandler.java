package com.virtualwallet.demo.Exception;

import com.virtualwallet.demo.DTO.ErrorResponse.ErrorResponseDTO;
import com.virtualwallet.demo.DTO.ErrorResponse.ErrorResponseDTOBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler
{

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handlerNotFound(NotFoundException notFoundException, HttpServletRequest request)
    {
        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(notFoundException.getMessage())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handlerBadRequest(BadRequestException badRequestException, HttpServletRequest request)
    {
        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(badRequestException.getMessage())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponseDTO handlerInsufficientFunds(InsufficientFundsException insufficientFundsException, HttpServletRequest request)
    {
        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.PAYMENT_REQUIRED.value())
                .error(HttpStatus.PAYMENT_REQUIRED.name())
                .message(insufficientFundsException.getMessage())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handlerServerError(Exception exception, HttpServletRequest request)
    {
        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(exception.getMessage() == null ? "Unknown Error" : exception.getMessage())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handlerMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request)
    {
        String message = "";
        if(exception.getMessage().contains("CryptoType"))
            message = "Invalid Crypto Type";

        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(message.isEmpty() ? exception.getMessage() : message)
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(
        {
            AuthenticationException.class,
            BadCredentialsException.class
        }
    )
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handlerAuthenticationException(Exception exception, HttpServletRequest request)
    {
        return new ErrorResponseDTOBuilder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.name())
                .message(exception.getMessage())
                .path(request.getServletPath())
                .build();
    }

}
