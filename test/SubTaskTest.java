import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    TaskManager taskManager = new InMemoryTaskManager();
    @Test
    void addNewSubTask() {

        final int epicId = taskManager.addNewEpic(new Epic("Test addNewEpic", "Test addNewEpic description"));

        SubTask subtask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", epicId);
        final int subtaskId = taskManager.addNewSubTask(subtask);
        final SubTask savedSubTask = taskManager.getSubTask(subtaskId);
        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subtask, savedSubTask, "Задачи не совпадают.");

    }

    @Test
    void addSubTaskInOtherSubTask() {

        final int epicId = taskManager.addNewEpic(new Epic("Test addNewEpic", "Test addNewEpic description"));
        final int subtaskId1 = taskManager.addNewSubTask(new SubTask("Test addNewEpic", "Test addNewEpic description", epicId));

        SubTask subtask2 = new SubTask("Test addNewEpic", "Test addNewEpic description", subtaskId1);

        final int subtaskId2 = taskManager.addNewSubTask(subtask2);
        assertNull(taskManager.getSubTask(subtaskId2));

    }

    @Test
    void getIdEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic(new Epic("Купить продукты на неделю", ""));
        int idSubTask1 = taskManager.addNewSubTask(new SubTask("Купить хлеб","",idEpic));
        assertEquals(1, taskManager.getSubTask(idSubTask1).getIdEpic());
    }

    @Test
    void checkTimeOfSubtask() {
        LocalDateTime ldt = LocalDateTime.now();

        Epic epic = new Epic("Epic1", "Epic1 description");

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int epicId = taskManager.addNewEpic(epic);

        SubTask subtask = new SubTask("Subtask1", "Subtask1 description", epicId, 5, ldt);
        int id = taskManager.addNewSubTask(subtask);

        assertEquals(taskManager.getSubTask(id).getStartTime().plus(Duration.ofMinutes(5)), taskManager.getSubTask(id).getEndTime());
    }
}