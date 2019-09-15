package ru.aschee.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aschee.task2.DTO.FolderEntityDTO;
import ru.aschee.task2.DTO.mapper.FolderMapper;
import ru.aschee.task2.domain.FolderEntity;
import ru.aschee.task2.service.impl.FolderEntityServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
public class MainController {
    private final FolderEntityServiceImpl folderEntityService;

    @Autowired
    public MainController(FolderEntityServiceImpl folderEntityService) {
        this.folderEntityService = folderEntityService;
    }

    @Autowired
    FolderMapper folderMapper;

    @GetMapping("/{id}")
    public FolderEntityDTO getFolder(@PathVariable("id") Long id) {
        Optional<FolderEntityDTO> folderEntity = folderEntityService.get(id);
        return folderEntity.get();
    }

    @PostMapping("/{id}")
    public FolderEntityDTO addFolder(@PathVariable("id") Long id, @RequestParam String folderName) {
        return folderEntityService.addFolder(id, folderName);
    }

    @PutMapping("/cut")
    public FolderEntityDTO cutFolder(@RequestParam Long sourceId, @RequestParam Long targetId) {
        return folderEntityService.cut(sourceId, targetId);
    }

    @PutMapping("/copy")
    public FolderEntityDTO copyFolder(@RequestParam Long sourceId, @RequestParam Long targetId) {
        return folderEntityService.copy(sourceId, targetId);
    }

    @PutMapping("/edit")
    public ResponseEntity<FolderEntityDTO> editNameFolder(@RequestParam Long idSelectFolder, @RequestParam String newNameFolder) {
       folderEntityService.editNameFolder(idSelectFolder, newNameFolder);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FolderEntity> deleteFolder(@RequestParam Long id) {
        folderEntityService.deleteAll(id);
        return ResponseEntity.ok().build();
    }
}
