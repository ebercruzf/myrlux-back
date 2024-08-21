package com.ebercruz.myrlux.back.util;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.message = message;
        this.path = path;
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
