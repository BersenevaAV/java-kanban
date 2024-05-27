import static org.junit.jupiter.api.Assertions.*;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
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
        assertEquals(epic.getStatus(), Status.NEW);
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
        SubTask subtask = new SubTask("Купить хлеб","",idEpic);
        taskManager.addNewSubTask(subtask);
        assertFalse(taskManager.getEpic(idEpic).getEpicSubTasks().isEmpty());
    }

    @Test
    void updateEpicStatus() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Купить продукты на неделю", "");
        int idEpic = taskManager.addNewEpic(epic);
        SubTask subtask = new SubTask("Купить хлеб","",idEpic);
        int idSubtask = taskManager.addNewSubTask(subtask);
        assertEquals(taskManager.getEpic(idEpic).getStatus(), Status.NEW);
        subtask.setStatus(Status.DONE);
        taskManager.updateSubTask(idSubtask,subtask);
        assertEquals(taskManager.getEpic(idEpic).getStatus(), Status.DONE);
        taskManager.addNewSubTask(new SubTask("Купить молоко","",idEpic));
        assertEquals(taskManager.getEpic(idEpic).getStatus(), Status.IN_PROGRESS);
    }

}