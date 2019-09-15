package ru.aschee.task2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.aschee.task2.DTO.FolderEntityDTO;
import ru.aschee.task2.DTO.mapper.FolderMapper;
import ru.aschee.task2.domain.FolderEntity;
import ru.aschee.task2.repository.FolderEntityRepository;
import ru.aschee.task2.service.FolderEntityService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EnableTransactionManagement
@Service
public class FolderEntityServiceImpl implements FolderEntityService {
    private final FolderEntityRepository folderEntityRepository;

    @Autowired
    FolderMapper folderMapper;

    public FolderEntityServiceImpl(FolderEntityRepository folderEntityRepository) {
        this.folderEntityRepository = folderEntityRepository;
    }

    @Transactional
    @Override
    public FolderEntityDTO addFolder(Long id, String name) {
        return folderMapper.convertEntityToDTO(folderEntityRepository.save(new FolderEntity(name, folderEntityRepository.findById(id).orElseThrow(), false)));
    }

    @Transactional
    @Override
    public Optional<FolderEntityDTO> get(Long id) {
        return Optional.of(folderMapper.convertEntityToDTO(folderEntityRepository.findById(id). orElseThrow()));
    }

    @Override
    @Transactional
    public void editNameFolder(Long id, String name) {
        Optional<FolderEntity> selectFolder = folderEntityRepository.findById(id);
        selectFolder.ifPresent(folderEntity -> folderEntity.setNameFolder(name));
        folderEntityRepository.save(selectFolder.orElseThrow());
    }

    @Transactional
    @Override
    public void deleteAll(Long id) {
        Optional<FolderEntity> folder = folderEntityRepository.findById(id);
        if (folder.isPresent()) {
            if (!folder.get().getChild().isEmpty()) {
                folderEntityRepository.deleteAllByParent(folder.get());
            }
            folderEntityRepository.delete(folder.get());
        }
    }

    @Transactional
    @Override
    public FolderEntityDTO copy(Long sourceId, Long targetId) {
        Optional<FolderEntity> source = folderEntityRepository.findById(sourceId);
        FolderEntity copy = cloneWithNoId(source.orElseThrow());
        copy.setParent(folderEntityRepository.findById(targetId).orElseThrow());
        FolderEntity saveCopy = folderEntityRepository.save(copy);
        FolderEntity set = settingParent(saveCopy);
        return folderMapper.convertEntityToDTO(folderEntityRepository.save(set));
    }

    private FolderEntity cloneWithNoId(FolderEntity folderEntity) {
        FolderEntity entity = new FolderEntity();
        entity.setActive(false);
        entity.setNameFolder(folderEntity.getNameFolder());
        entity.setChild(recursive(folderEntity.getChild()));
        return entity;
    }

    private Set<FolderEntity> recursive(Set<FolderEntity> child) {
        if (child == null) {
            return null;
        }
        Set<FolderEntity> dtoChild = new HashSet<>();
        for (FolderEntity currentChild : child) {
            dtoChild.add(cloneWithNoId(currentChild));
        }
        return dtoChild;
    }

    private Set<FolderEntity> recursivePa(Set<FolderEntity> child, FolderEntity folderEntity) {
        if (child == null) {
            return null;
        }
        Set<FolderEntity> dtoChild = new HashSet<>();
        for (FolderEntity currentChild : child) {
            currentChild.setParent(folderEntity);
            dtoChild.add(settingParent(currentChild));
        }
        return dtoChild;
    }

    private FolderEntity settingParent(FolderEntity entity) {
        entity.setChild(recursivePa(entity.getChild(), entity));
        return entity;
    }

    @Transactional
    @Override
    public FolderEntityDTO cut(Long sourceId, Long targetId) {
        Optional<FolderEntity> source = folderEntityRepository.findById(sourceId);
        source.ifPresent(folderEntity -> folderEntity.setParent(folderEntityRepository.findById(targetId).orElseThrow()));
        return folderMapper.convertEntityToDTO(folderEntityRepository.save(source.orElseThrow()));
    }
}
