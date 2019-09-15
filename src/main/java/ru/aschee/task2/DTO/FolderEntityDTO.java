package ru.aschee.task2.DTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderEntityDTO {
    private Long id;
    @NonNull
    @NotEmpty
    @Size(max = 15)
    @Pattern(regexp = "^[a-z0-9_]{1,15}$")
    private String nameFolder;
    private FolderEntityDTO parent;

    private boolean isActive;
    private Set<FolderEntityDTO> child;


}
