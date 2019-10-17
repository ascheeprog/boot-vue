package ru.aschee.task2.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aschee.task2.DTO.FolderDTO;
import ru.aschee.task2.domain.Folder;
import ru.aschee.task2.repository.FolderEntityRepository;
import ru.aschee.task2.service.FolderService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FolderServiceImplTest {

    @Autowired
    private FolderService folderService;

    @MockBean
    private FolderEntityRepository repository;

    private Folder folder;

    @Before
    public void setUp() {
        folder = new Folder();
        folder.setId(1L);
        folder.setNameFolder("firstName");
        folder.setParent(null);
        folder.setActive(false);
    }

    @Test
    public void givenName_whenSaveFolder_thenReturnSaveFolder() {
        folder.setNameFolder("first");

        when(repository.save(folder)).thenReturn(folder);

        FolderDTO folderDTO = folderService.addFolder(folder);

        assertThat(folderDTO.getNameFolder(), is("first"));
        verify(repository, times(1)).save(folder);
    }

    @Test
    public void givenId_whenGetFolder_thenSuccess() {
        long id = folder.getId();

        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(folder));

        Optional<FolderDTO> folderDTO = folderService.get(id);

        folderDTO.ifPresent(entity -> assertThat(folderDTO.get().getNameFolder(), is(folder.getNameFolder())));
        folderDTO.ifPresent(entity -> assertThat(folderDTO.get().getId(), is(folder.getId())));

        verify(repository, times(1)).findById(id);
    }

    @Test(expected = NullPointerException.class)
    public void givenFolderId_whenGetFolder_thenFail() {
        doReturn(java.util.Optional.of(new Folder())).when(repository).findById(folder.getId());
        folderService.get(folder.getId()).orElseThrow();
    }

    @Test
    public void givenFolderName_whenEditNameFolder_thenNameChanged() {
        when(repository.findById(folder.getId())).thenReturn(java.util.Optional.ofNullable(folder));
        when(repository.save(folder)).thenReturn(folder);

        folderService.editNameFolder(folder.getId(), "second");

        assertThat(folder.getNameFolder(), is("second"));

        verify(repository, times(1)).findById(folder.getId());
        verify(repository, times(1)).save(folder);
    }

    @Test
    public void givenFolderId_whenDeleteAll_thenChildAndFolderDeleted() {
        Folder one = new Folder();

        one.setId(2L);
        one.setNameFolder("firstName");

        when(repository.save(one)).thenReturn(one);
        when(repository.findById(one.getId())).thenReturn(java.util.Optional.of(one));

        doNothing().when(repository).deleteAllByParent(one);
        doNothing().when(repository).delete(one);

        folderService.addFolder(one);

        folderService.deleteAll(one.getId());

        verify(repository, times(1)).deleteAllByParent(one);
        verify(repository, times(1)).delete(one);
    }

}