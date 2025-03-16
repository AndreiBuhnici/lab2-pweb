package com.example.lab2.exception;

public class FileAlreadyExistsException extends Exception {
    public FileAlreadyExistsException(String message) {
        super(message);
    }
}
