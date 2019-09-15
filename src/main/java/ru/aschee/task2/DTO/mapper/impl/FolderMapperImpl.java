package ru.aschee.task2.DTO.mapper.impl;

import org.springframework.stereotype.Component;
import ru.aschee.task2.DTO.FolderEntityDTO;
import ru.aschee.task2.DTO.mapper.FolderMapper;
import ru.aschee.task2.domain.FolderEntity;
import java.util.HashSet;
import java.util.Set;

@Component
public class FolderMapperImpl implements FolderMapper {

    private Set<FolderEntityDTO> recursive(Set<FolderEntity> child) {
        if (child == null) {
            return null;
        }
        Set<FolderEntityDTO> dtoChild = new HashSet<>();
        for (FolderEntity currentChild : child) {
            dtoChild.add(convertEntityToDTO(currentChild));
        }
        return dtoChild;
    }

    @Override
    public FolderEntityDTO convertEntityToDTO(FolderEntity folderEntity) {
        if (folderEntity == null) {
            return null;
        }
        FolderEntityDTO folderEntityDTO = new FolderEntityDTO();
        folderEntityDTO.setId(folderEntity.getId());
        folderEntityDTO.setNameFolder(folderEntity.getNameFolder());
        folderEntityDTO.setActive(folderEntity.isActive());
        folderEntityDTO.setChild(recursive(folderEntity.getChild()));

        return folderEntityDTO;
    }
}
