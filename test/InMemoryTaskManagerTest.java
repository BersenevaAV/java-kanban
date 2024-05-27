import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void getHistoryOfTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic(new Epic("Купить продукты на неделю", ""));
        int idSubTask1 = taskManager.addNewSubTask(new SubTask("Купить хлеб","",idEpic));
        assertFalse(taskManager.getHistory().isEmpty());
    }

}