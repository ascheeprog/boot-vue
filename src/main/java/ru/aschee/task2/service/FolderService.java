package ru.aschee.task2.service;

import ru.aschee.task2.DTO.FolderEntityDTO;

import java.util.Optional;

public interface FolderEntityService {
    FolderEntityDTO addFolder(Long id,String name);
    Optional<FolderEntityDTO> get(Long id);
    void editNameFolder(Long id, String name);
    void deleteAll(Long id);
    FolderEntityDTO copy(Long sourceId, Long targetId);
    FolderEntityDTO cut(Long sourceId,Long targetId);
}
