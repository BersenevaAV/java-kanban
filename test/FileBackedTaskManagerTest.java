import managers.FileBackedTaskManager;
import managers.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    FileBackedTaskManager fM;

    @BeforeEach
    public void beforeEach() {
        fM = new FileBackedTaskManager();

        try (FileWriter fw = new FileWriter(new File("file.txt"), StandardCharsets.UTF_8)) {
            fw.write("");
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    @Test
    void createEmptyFManager() {
        assertTrue(fM.getTasks().isEmpty());
        assertTrue(fM.getEpics().isEmpty());
        assertTrue(fM.getSubTasks().isEmpty());
    }

    @Test
    void addInFile() throws IOException {
        int idTask = fM.addNewTask(new Task("Задача", "Описание"));
        int idEpic = fM.addNewEpic(new Epic("Эпик", "Описание"));
        int idSubtask = fM.addNewSubTask(new SubTask("Подзадача", "Описание", idEpic));

        FileBackedTaskManager fM2 = FileBackedTaskManager.loadFromFile(new File("file.txt"));
        assertEquals(idTask, fM2.getTask(idTask).getId());
        assertEquals(idEpic, fM2.getEpic(idEpic).getId());
        assertEquals(idSubtask, fM2.getSubTask(idSubtask).getId());
    }
}