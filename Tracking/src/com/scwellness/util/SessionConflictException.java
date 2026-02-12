package com.scwellness.util;

public class SessionConflictException extends Exception {

    public SessionConflictException(String msg) {
        super(msg);
    }

    public String toString() {
        return getMessage();
    }
}
