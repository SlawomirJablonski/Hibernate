package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskListDaoTestSuite {
    @Autowired
    private TaskListDao taskListDao;

    @Test
    public void testFindByListName() {
        //Given
        TaskList taskList = new TaskList("dreams", "New Year's resolutions");
        taskListDao.save(taskList);
        String questListName = taskList.getListName();

        //When
        List<TaskList> questList = taskListDao.findByListName(questListName);

        //Then
        Assert.assertEquals(1,questList.size());

        //CleanUp
        int id = questList.get(0).getId();
        taskListDao.delete(id);
    }
}
