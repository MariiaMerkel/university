package ru.msu.university.service;

import org.springframework.web.multipart.MultipartFile;
import ru.msu.university.entities.Avatar;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar getAvatarByStudent(Long id);
}
