//package ru.aschee.task2.domain;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.aschee.task2.DTO.FolderEntityDTO;
//import ru.aschee.task2.service.impl.FolderEntityServiceImpl;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.TreeSet;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//public class EntityTest {
//
//    @Autowired
//    private FolderEntityServiceImpl folderEntityService;
//
//    @Test
//    public void create_new_node_and_get_correct_value() {
//
//        FolderEntityDTO folderEntity = folderEntityService.addFolder(new FolderEntity("first", null,false));
//        FolderEntityDTO folderEntity1 = folderEntityService.addFolder(new FolderEntity("second", folderEntity,false));
//        FolderEntityDTO folderEntity2 = folderEntityService.addFolder(new FolderEntity("three", folderEntity,false));
//
//        TreeSet<FolderEntity> set = new TreeSet<>((a,b)->a.getNameFolder().compareToIgnoreCase(b.getNameFolder()));
//
//        set.add(folderEntity1);
//        set.add(folderEntity2);
//
//        folderEntity.setChild(set);
//
//        Assert.assertNotNull(folderEntity);
//        Assert.assertNotNull(folderEntity1);
//        Assert.assertNotNull(folderEntity2);
//
//        Assert.assertNotEquals(null, folderEntity);
//        Assert.assertNotEquals(null, folderEntity1);
//        Assert.assertNotEquals(null, folderEntity2);
//        Assert.assertNotEquals(java.util.Optional.ofNullable(folderEntity.getId()), Optional.empty());
//
//        Assert.assertEquals(set,folderEntity.getChild());
//        Assert.assertEquals(folderEntity1.getParent(),folderEntity);
//        Assert.assertEquals(folderEntity2.getParent(),folderEntity);
//    }
//}
