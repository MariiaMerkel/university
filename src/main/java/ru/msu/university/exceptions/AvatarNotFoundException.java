package ru.msu.university.exceptions;

public class AvatarNotFoundException extends RuntimeException {

    public AvatarNotFoundException() {
        super("Аватарка не найдена");
    }
}
