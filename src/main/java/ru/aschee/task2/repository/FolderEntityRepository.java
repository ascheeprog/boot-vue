package ru.aschee.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aschee.task2.domain.FolderEntity;

public interface FolderEntityRepository extends JpaRepository<FolderEntity, Long> {
    void deleteAllByParent(FolderEntity parent);
}
