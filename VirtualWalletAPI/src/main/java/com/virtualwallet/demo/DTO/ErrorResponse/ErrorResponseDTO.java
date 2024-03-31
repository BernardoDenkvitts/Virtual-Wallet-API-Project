package com.virtualwallet.demo.DTO.ErrorResponse;

import java.time.ZonedDateTime;

public class ErrorResponseDTO{
    private ZonedDateTime timestamp;
    private Integer status;
    private String message;
    private String error;
    private String path;

    public ErrorResponseDTO(){}

    public ErrorResponseDTO(ZonedDateTime timestamp, Integer status, String message, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }


    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    protected void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    protected void setStatus(Integer status) {
        this.status = status;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    protected void setError(String error) {
        this.error = error;
    }

    protected void setPath(String path) {
        this.path = path;
    }
}
