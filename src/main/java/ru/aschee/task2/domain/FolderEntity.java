package ru.aschee.task2.domain;

import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String nameFolder;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FolderEntity parent;

    private boolean isActive;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<FolderEntity> child;

    public FolderEntity() {
    }

    public FolderEntity(@NonNull String nameFolder, FolderEntity parent, boolean isActive) {
        this.nameFolder = nameFolder;
        this.parent = parent;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public Set<FolderEntity> getChild() {
        return child;
    }

    public void setChild(Set<FolderEntity> child) {
        this.child = child;
    }

    public FolderEntity getParent() {
        return parent;
    }

    public void setParent(FolderEntity parent) {
        this.parent = parent;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
