package com.scwellness.util;

public class ValidationException extends Exception {

    public ValidationException(String msg) {
        super(msg);
    }
    public String toString() {
        return getMessage();
    }
}
