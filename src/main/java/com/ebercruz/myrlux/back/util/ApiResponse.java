package com.ebercruz.myrlux.back.util;

import java.util.List;

public class ApiResponse<T> {
    private T data;
    private String message;
    private String code;
    private boolean success;
    private List<String> errors;

    // Constructor para respuestas exitosas con datos
    public ApiResponse(T data, String message) {
        this(data, message, "200", true);
    }

    // Constructor para respuestas de error
    public ApiResponse(String message, String errorCode) {
        this(null, message, errorCode, false);
    }

    // Constructor completo para máxima flexibilidad
    public ApiResponse(T data, String message, String code, boolean success) {
        this(data, message, code, success, null);
    }


    // Nuevo constructor que incluye la lista de errores
    public ApiResponse(T data, String message, String code, boolean success, List<String> errors) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.success = success;
        this.errors = errors;
    }


    // Getters y setters

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}