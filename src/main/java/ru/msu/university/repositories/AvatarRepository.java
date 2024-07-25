package ru.msu.university.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.entities.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}
