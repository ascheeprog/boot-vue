package ru.aschee.task2.service;

import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aschee.task2.service.impl.FolderEntityServiceImpl;


@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTest {
    @MockBean
    private FolderEntityService folderService;

    @Spy
    private FolderEntityServiceImpl folderServiceImpl;

}
