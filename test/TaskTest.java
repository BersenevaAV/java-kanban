import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


class TaskTest {

    @Test
    void addNewTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description");

        final int taskId = taskManager.addNewTask(task);
        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final Map<Integer, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(1), "Задачи не совпадают.");

    }

    @Test
    void equalsOfTasks() {
        Task task1 = new  Task("Test1 addNewTask", "Test1 addNewTask description");
        Task task2 = new Task("Test2 addNewTask", "Test2 addNewTask description");
        assertNotEquals(task1, task2, "Задачи не совпадают.");
    }

    @Test
    void checkTimeOfTask() {
        LocalDateTime ldt = LocalDateTime.now();
        Task task1 = new  Task("Test1", "Test1 description", 5, ldt);
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int id1 = taskManager.addNewTask(task1);
        assertEquals(taskManager.getTask(id1).getStartTime().plus(Duration.ofMinutes(5)), taskManager.getTask(id1).getEndTime());
    }

}