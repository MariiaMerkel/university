package ru.msu.university.exceptions;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Студент с id=" + id + " не найден");
    }

}
