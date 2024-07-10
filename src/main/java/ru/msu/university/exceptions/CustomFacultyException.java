package ru.msu.university.exceptions;

public class CustomFacultyException extends RuntimeException {
    public CustomFacultyException(String message, Throwable cause) {
        super(message, cause);
    }
}
