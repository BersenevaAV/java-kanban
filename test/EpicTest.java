import static org.junit.jupiter.api.Assertions.*;

import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;

class EpicTest {

    @Test
    void addNewEpic() {
        int id = 5;
        Epic expectedEpic = new Epic("Купить продукты на неделю", "", 5);
        assertEquals(id, expectedEpic.getId());
    }


    @Test
    void getEpicWithEmptySubTasks() {
        Epic expectedEpic = new Epic("Купить продукты на неделю", "", 5);
        assertTrue(expectedEpic.getEpicSubTasks().isEmpty());
    }

    @Test
    void getEpicWithSubTasks() {
        TaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic("Купить продукты на неделю", "");
        taskManager.addNewSubTask("Купить хлеб","",idEpic);

        assertFalse(taskManager.getEpic(idEpic).getEpicSubTasks().isEmpty());
    }
}