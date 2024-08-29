package com.cassio.nicepay.exception;

public class BusinessException extends RuntimeException {
    public BusinessException() {
        super("Sorry, there was an error processing your request. Please try again later or contact support if the issue persists");
    }
}