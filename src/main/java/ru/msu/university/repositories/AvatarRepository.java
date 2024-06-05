package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.entities.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
