import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void getHistoryOfTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic(new Epic("Купить продукты на неделю", ""));
        taskManager.addNewSubTask(new SubTask("Купить хлеб","",idEpic));
        assertFalse(taskManager.getHistory().isEmpty());
    }

    @Test
    void getPrioritizedTasks() {
        LocalDateTime ldt = LocalDateTime.now();

        Task task1 = new  Task("Test1", "Test1 description", 5, ldt);
        Task task2 = new  Task("Test1", "Test1 description", 5, ldt.minus(Duration.ofMinutes(50)));
        Task task3 = new  Task("Test1", "Test1 description", 5, ldt.plus(Duration.ofMinutes(20)));

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewTask(task3);

        assertEquals(3, taskManager.getPrioritizedTasks().size());

        assertTrue(taskManager.getPrioritizedTasks().contains(task1));
        assertTrue(taskManager.getPrioritizedTasks().contains(task2));
        assertTrue(taskManager.getPrioritizedTasks().contains(task3));

        assertEquals(task2, taskManager.getPrioritizedTasks().first());
        assertEquals(task3, taskManager.getPrioritizedTasks().last());
    }

}