package ru.aschee.task2.domain;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Validated
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @NotNull
    @NotBlank
    private String nameFolder;

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FolderEntity parent;

    private boolean isActive;

    @OneToMany(mappedBy = "parent",
            cascade =
                    {CascadeType.REFRESH,
                            CascadeType.REMOVE,
                            CascadeType.MERGE,
                            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    private Set<FolderEntity> child;

    public FolderEntity() {
    }

    public FolderEntity(
            @NotNull @NotBlank String nameFolder, FolderEntity parent, boolean isActive) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderEntity that = (FolderEntity) o;
        return isActive == that.isActive &&
                Objects.equals(id, that.id) &&
                nameFolder.equals(that.nameFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameFolder, parent, isActive, child);
    }
}
