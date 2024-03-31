package com.virtualwallet.demo.DTO.ErrorResponse;

import java.time.ZonedDateTime;

public class ErrorResponseDTOBuilder
{
    private final ErrorResponseDTO errorResponseDTO;
    public ErrorResponseDTOBuilder(){
        this.errorResponseDTO = new ErrorResponseDTO();
    }

    public ErrorResponseDTOBuilder status(Integer status)
    {
        errorResponseDTO.setStatus(status);
        return this;
    }

    public ErrorResponseDTOBuilder message(String message)
    {
        errorResponseDTO.setMessage(message);
        return this;
    }

    public ErrorResponseDTOBuilder error(String error)
    {
        this.errorResponseDTO.setError(error);
        return this;
    }

    public ErrorResponseDTOBuilder path(String path)
    {
        errorResponseDTO.setPath(path);
        return this;
    }

    public ErrorResponseDTO build()
    {
        return new ErrorResponseDTO(
            ZonedDateTime.now(),
            errorResponseDTO.getStatus(),
            errorResponseDTO.getMessage(),
            errorResponseDTO.getError(),
            errorResponseDTO.getPath()
        );
    }

}
