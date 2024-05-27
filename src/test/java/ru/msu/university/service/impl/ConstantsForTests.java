package ru.msu.university.service.impl;

import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;

public class ConstantsForTests {
    public static final Student ALEX = new Student("Alex", 40);
    public static final Student ALEX_EXPECTED = new Student(1L, "Alex", 40);
    public static final Student SERGEY = new Student("Sergey", 30);
    public static final Student SERGEY_EXPECTED = new Student(2l, "Sergey", 30);
    public static final Student MARIIA = new Student("Mariia", 35);
    public static final Student MARIIA_EXPECTED = new Student(3L, "Mariia", 35);
    public static final Student TATYANA = new Student("Tatyana", 50);
    public static final Student TATYANA_EXPECTED = new Student(4L, "Tatyana", 50);
    public static final Student IVAN = new Student("Ivan", 60);
    public static final Student IVAN_EXPECTED = new Student(1L, "Ivan", 60);

    public static final Faculty BIOLOGY = new Faculty("Biology", "Green");
    public static final Faculty BIOLOGY_EXPECTED = new Faculty(1L, "Biology", "Green");
    public static final Faculty MATHEMATICS = new Faculty("Mathematics", "Grey");
    public static final Faculty MATHEMATICS_EXPECTED = new Faculty(2L, "Mathematics", "Grey");
    public static final Faculty ECONOMICS = new Faculty("Economics", "Pink");
    public static final Faculty ECONOMICS_EXPECTED = new Faculty(3L, "Economics", "Pink");
    public static final Faculty CHEMICAL = new Faculty("Сhemical", "Blue");
    public static final Faculty CHEMICAL_EXPECTED = new Faculty(4L, "Сhemical", "Blue");
    public static final Faculty PHILOLOGY = new Faculty("Philology", "Red");
    public static final Faculty PHILOLOGY_EXPECTED = new Faculty(1L, "Philology", "Red");
}