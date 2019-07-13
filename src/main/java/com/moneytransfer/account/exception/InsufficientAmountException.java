package com.moneytransfer.account.exception;

public class InsufficientAmountException extends Exception {
    public InsufficientAmountException(String message) {
        super(message);
    }
}
