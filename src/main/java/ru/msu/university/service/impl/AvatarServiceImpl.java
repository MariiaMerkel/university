package ru.msu.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.msu.university.entities.Avatar;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.AvatarNotFoundException;
import ru.msu.university.repositories.AvatarRepository;
import ru.msu.university.service.AvatarService;
import ru.msu.university.service.StudentService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentServiceImpl studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.get(studentId);
        Path filePath = Path.of(avatarsDir, student.getName() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatarByStudent(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setPreview(generateImagePreview(filePath));
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        logger.debug("uploaded avatar {}", avatar);
    }

    private Avatar findAvatarByStudent(Long studentId) {
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        logger.debug("finded avatar of student {}", studentId);
        return avatar;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            logger.debug("generated avatar");
            return baos.toByteArray();
        }
    }

    @Override
    public Avatar getAvatarByStudent(Long studentId) {
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseThrow(AvatarNotFoundException::new);
        logger.debug("founded avatar of student", studentId);
        return avatar;
    }

    @Override
    public List<Avatar> getAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Avatar> avatarList = avatarRepository.findAll(pageRequest).getContent();
        logger.debug("Founded all avatars {}", avatarList);
        return avatarList;
    }
}
