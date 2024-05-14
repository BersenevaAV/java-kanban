import static org.junit.jupiter.api.Assertions.*;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

class EpicTest {

    TaskManager taskManager = new InMemoryTaskManager();
    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        final int epicId = taskManager.addNewEpic(epic);
        final Epic savedEpic = taskManager.getEpic(epicId);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

    }

    @Test
    void getEpicWithEmptySubTasks() {
        Epic expectedEpic = new Epic("Купить продукты на неделю", "");
        assertTrue(expectedEpic.getEpicSubTasks().isEmpty());
    }

    @Test
    void getEpicWithSubTasks() {
        TaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic(new Epic("Купить продукты на неделю", ""));
        taskManager.addNewSubTask(new SubTask("Купить хлеб","",idEpic));

        assertFalse(taskManager.getEpic(idEpic).getEpicSubTasks().isEmpty());
    }
}