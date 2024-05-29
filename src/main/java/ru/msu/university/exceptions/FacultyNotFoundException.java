package ru.msu.university.exceptions;

public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(Long id) {
        super("Факультет с id=" + id + " не найден");
    }
    public FacultyNotFoundException(String color) {
        super("Факультет \"" + color + "\" не найден");
    }

}
