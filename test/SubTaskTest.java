import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    void getIdEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        int idEpic = taskManager.addNewEpic("Купить продукты на неделю", "");
        int idSubTask1 = taskManager.addNewSubTask("Купить хлеб","",idEpic);
        assertEquals(1, taskManager.getSubTask(idSubTask1).getIdEpic());
    }
}