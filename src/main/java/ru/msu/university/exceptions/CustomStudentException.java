package ru.msu.university.exceptions;

public class CustomStudentException extends RuntimeException {

    public CustomStudentException(String message, Throwable cause) {
        super(message, cause);
    }
}
