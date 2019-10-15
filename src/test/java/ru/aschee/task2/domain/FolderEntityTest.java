package ru.aschee.task2.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aschee.task2.repository.FolderEntityRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FolderEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    FolderEntityRepository folderEntityRepository;

    private FolderEntity firstFolder;

    private FolderEntity secondFolder;

    private FolderEntity thirdFolder;

    private Validator validate;

    @Before
    public void setUp() {
        firstFolder = new FolderEntity("first", null, false);
        secondFolder = new FolderEntity("second", null, false);
        thirdFolder = new FolderEntity("third", null, false);
        validate = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenFolderEntity_whenSaveFolderEntity_thenSuccess() {
        FolderEntity savedFirstFolder = this.entityManager.persistAndFlush(firstFolder);
        assertThat(savedFirstFolder.getNameFolder(), is("first"));
        assertThat(savedFirstFolder.getId(), is(notNullValue()));
    }

    @Test
    public void givenNullName_whenCreateFolderEntity_thenTwoConstraintViolations() {
        FolderEntity entity = new FolderEntity(null, null, false);
        Set<ConstraintViolation<FolderEntity>> violation = validate.validate(entity);
        assertThat(violation.size(), is(2));
    }

    @Test
    public void givenEmptyName_whenCreateFolderEntity_thenOneConstraintViolations() {
        FolderEntity entity = new FolderEntity("", null, false);
        Set<ConstraintViolation<FolderEntity>> violation = validate.validate(entity);
        assertThat(violation.size(), is(1));
    }

    @Test
    public void givenFolderEntity_whenSetParentFolder_thenSuccess() {
        secondFolder.setParent(firstFolder);

        FolderEntity saveFirstFolder = this.entityManager.persistFlushFind(firstFolder);
        FolderEntity saveSecondFolder = this.entityManager.persistFlushFind(secondFolder);

        assertThat(saveFirstFolder.getParent(), is(nullValue()));
        assertThat(saveSecondFolder.getParent(), is(secondFolder.getParent()));
    }

    @Test
    public void givenFolderEntity_whenSetChildFolder_thenSuccess() {

        Set<FolderEntity> set = new HashSet<>();
        set.add(secondFolder);
        firstFolder.setChild(set);

        FolderEntity savedFirstFolder = this.entityManager.persistFlushFind(firstFolder);

        assertThat(savedFirstFolder.getChild().size(), is(notNullValue()));

        for (FolderEntity child : savedFirstFolder.getChild()) {
            assertThat(child, is(secondFolder));
        }
    }

    @Test
    public void givenFolderEntityRepository_whenInitializedByDbUnit_thenFindById() {

        Optional<FolderEntity> folderEntity = folderEntityRepository.findById(1L);

        assertThat(folderEntity, is(notNullValue()));

        folderEntity.ifPresent(entity -> assertThat(entity, is(notNullValue())));
        folderEntity.ifPresent(entity -> assertThat(entity.getNameFolder(), is("FirstFolder")));
    }

    @Test
    public void givenFolderEntityRepository_whenSaveFolderEntity_thenFindById() {
        FolderEntity first = folderEntityRepository.save(firstFolder);
        assertThat(first, is(firstFolder));
        assertThat(firstFolder, is(folderEntityRepository.findById(first.getId()).orElse(null)));
    }

    @Test
    public void givenFolderEntityRepository_whenSaveThreeFolderEntity_thenDeleteAllByParent() {

        secondFolder.setParent(firstFolder);
        thirdFolder.setParent(firstFolder);

        FolderEntity first = folderEntityRepository.save(firstFolder);
        FolderEntity second = folderEntityRepository.save(secondFolder);
        FolderEntity third = folderEntityRepository.save(thirdFolder);

        assertThat(first, is(firstFolder));
        assertThat(second, is(secondFolder));
        assertThat(third, is(thirdFolder));

        long count = folderEntityRepository.count();

        folderEntityRepository.deleteAllByParent(firstFolder);

        assertThat(count - 2L, is(folderEntityRepository.count()));
    }
}
