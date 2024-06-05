package ru.msu.university.entities;

import jakarta.persistence.*;

@Entity
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String filePath;
    Long fileSize;
    String mediaType;
    byte[] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;


}
