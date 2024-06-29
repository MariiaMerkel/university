package ru.msu.university.exceptions;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Студент с id=" + id + " не найден");
    }

    public StudentNotFoundException(String name) {
        super("Студент с именем=" + name + " не найден");
    }

    public StudentNotFoundException(int age) {
        super("Студент с возрастом=" + age + " не найден");
    }

}
