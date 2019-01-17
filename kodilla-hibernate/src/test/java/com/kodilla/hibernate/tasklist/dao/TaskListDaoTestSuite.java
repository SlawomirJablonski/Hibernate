package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.task.Task;
import com.kodilla.hibernate.task.TaskFinancialDetails;
import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskListDaoTestSuite {
    @Autowired
    private TaskListDao taskListDao;
    private static final String LISTNAME = "TO_DO";

    @Test
    public void testFindByListName() {
        //Given
        TaskList taskList = new TaskList("dreams", "New Year's resolutions");
        TaskList taskList1 = new TaskList("achievements", "Last Year's resolutions");
        taskListDao.save(taskList);
        taskListDao.save(taskList1);
        String questListName = taskList.getListName();
        String questListName1 = taskList1.getListName();

        //When
        List<TaskList> questList = taskListDao.findByListName(questListName);
        TaskList firstTaskList = questList.get(0);
        TaskList secondTaskList = questList.get(1);
        String nameOfQuestList = firstTaskList.getListName();
        String descriptionOfQuestList = firstTaskList.getDescription();
        String descriptionOfQuestList1 = secondTaskList.getDescription();
        int dreamsId = firstTaskList.getId();
        int achievementsId = secondTaskList.getId();

        //Then
        Assert.assertEquals(2,questList.size());
        Assert.assertEquals("dreams",nameOfQuestList);
        Assert.assertEquals("New Year's resolutions",descriptionOfQuestList);
        Assert.assertNotEquals("New Year's resolutions",descriptionOfQuestList1);
        Assert.assertNotEquals(dreamsId,achievementsId);
        Assert.assertTrue(questListName.equals(questListName1) && questListName1.equals(questListName));
        Assert.assertEquals(questListName.hashCode(), questListName1.hashCode());

        //CleanUp
        taskListDao.deleteAll();
    }
    @Test
    public void testTaskListDaoSaveWithTasks(){
        //Given
        Task task = new Task("Test: Learn Hibernate", 14);
        Task task2 = new Task("Test: Write some entities", 3);
        TaskFinancialDetails tfd = new TaskFinancialDetails(new BigDecimal(20), false);
        TaskFinancialDetails tfd2 = new TaskFinancialDetails(new BigDecimal(10), false);
        task.setTaskFinancialDetails(tfd);
        task2.setTaskFinancialDetails(tfd2);
        TaskList taskList = new TaskList(LISTNAME, "ToDo tasks");
        taskList.getTasks().add(task);
        taskList.getTasks().add(task2);
        task.setTaskList(taskList);
        task2.setTaskList(taskList);
        //When
        taskListDao.save(taskList);
        int id = taskList.getId();

        //Then
        Assert.assertNotEquals(0, id);

        //CleanUp
        //taskListDao.delete(id);

    }
}
