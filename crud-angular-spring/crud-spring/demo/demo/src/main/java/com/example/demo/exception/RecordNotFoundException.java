package com.example.demo.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(Long id) {
        super("Record not found: " + id);
    }

}
