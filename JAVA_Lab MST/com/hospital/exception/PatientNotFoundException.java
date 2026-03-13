package com.hospital.exception;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}