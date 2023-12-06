import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    ////добавление задач
    void addNewTask(Task task);
    void addNewEpic(Epic epic);
    void addNewSubTask(SubTask subTask, int idEpic);

    ////получение всех задач
    Map<Integer, Task> getTasks();
    Map<Integer, Epic> getEpics();
    Map<Integer, SubTask> getSubTasks();

    ////очищение списка задач
    void clearAllTasks();
    void clearAllEpics();
    void clearAllSubTasks();

    ////получение задачи по id
    Task getTask(int id);
    Epic getEpic(int id);
    SubTask getSubTask(int id);

    ////обновление задачи по id
    void updateTask(int id, Task newTask);
    void updateEpic(int id, Epic newEpic);
    void updateSubTask(int id, SubTask newSubTask);

    ////удаление задачи по id
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubTask(int id);

    ////получение подзадач конкретного эпика
    ArrayList<SubTask> getSubTasksOfEpic(int idEpic);

    ////установить статус задачи
    void setStatusTask(int id, Status status);
    void setStatusEpic(int id, Status status);
    void setStatusSubTask(int id, Status status);
    public List<Task> getHistory();

}
