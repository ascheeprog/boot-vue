package ru.aschee.task2.DTO.mapper;

import ru.aschee.task2.DTO.FolderEntityDTO;
import ru.aschee.task2.domain.FolderEntity;


public interface FolderMapper {
    FolderEntityDTO convertEntityToDTO(FolderEntity folderEntity);
}
