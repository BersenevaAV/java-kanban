import static org.junit.jupiter.api.Assertions.*;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.Map;


class TaskTest {

    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewTask() {

        Task task = new Task("Test addNewTask", "Test addNewTask description", 1);

        final int taskId = taskManager.addNewTask("Test addNewTask", "Test addNewTask description");
        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final Map<Integer,Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(1), "Задачи не совпадают.");
    }

}