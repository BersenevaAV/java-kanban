package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TaskManager {
    ////добавление задач
    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubTask(SubTask subTask);

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

    List<Task> getHistory();
}
