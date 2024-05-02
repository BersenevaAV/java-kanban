import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addNewEpic() {

        TaskManager taskManager = new InMemoryTaskManager();

        int idEpic = taskManager.addNewEpic("Купить продукты на неделю", "");
        Epic expectedEpic = new Epic("Купить продукты на неделю", "", 1);

        assertEquals(expectedEpic, taskManager.getEpic(idEpic));
    }

    @Test
    void addNewSabTask() {

        TaskManager taskManager = new InMemoryTaskManager();

        int idEpic = taskManager.addNewEpic("Купить продукты на неделю", "");
        int idSubTask1 = taskManager.addNewSubTask("Купить хлеб","",idEpic);
        Task expectedSubTask = new SubTask("Купить хлеб", "", 1, idSubTask1);

        assertEquals(expectedSubTask, taskManager.getSubTask(idSubTask1));

    }

    @Test
    void getHistoryOfTaskManager() {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic("Купить продукты на неделю", "");
        int idSubTask1 = taskManager.addNewSubTask("Купить хлеб","",idEpic);
        assertFalse(taskManager.getHistory().isEmpty());

    }

}