package ru.msu.university.exceptions;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class AppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<String> handlerStudentNotFound(StudentNotFoundException ex) {
        logger.error("StudentNotFoundException {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerFacultyNotFound(FacultyNotFoundException ex) {
        logger.error("FacultyNotFoundException {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerNoSuchElement(NoSuchElementException ex) {
        logger.error("NoSuchElementException {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerAvatarNotFound(AvatarNotFoundException ex) {
        logger.error("AvatarNotFoundException {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        logger.error("MaxUploadSizeExceededException {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Слишком большой файл");
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerCustomFacultyException(CustomFacultyException ex) {
        logger.error("CustomFacultyException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerCustomStudentException(CustomStudentException ex) {
        logger.error("CustomStudentException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
