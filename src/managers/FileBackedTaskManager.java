package managers;

import tasks.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file = new File("file.txt");

    private void save() {
        try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8)) {
            fw.write("id,type,name,status,description,epic" + "\n");
            for (Task task: super.tasks.values()) {
                fw.write(task.toString() + "\n");
            }
            for (Task task: super.epics.values()) {
                fw.write(task.toString() + "\n");
            }
            for (Task task: super.subTasks.values()) {
                fw.write(task.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public static Task fromString(String value) {
        String[] taskStr = value.split(",");
        Task task;
        if (TypeTask.valueOf(taskStr[1]).equals(TypeTask.TASK)) {
            task = new Task(taskStr[2], taskStr[4]);
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));
        } else if (TypeTask.valueOf(taskStr[1]).equals(TypeTask.EPIC)) {
            task = new Epic(taskStr[2], taskStr[4]);
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));
        } else if (TypeTask.valueOf(taskStr[1]).equals(TypeTask.SUBTASK)) {
            task = new SubTask(taskStr[2], taskStr[4],Integer.parseInt(taskStr[5]));
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));
        } else {
            task = null;
        }
        return task;
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileBackedTaskManager fManager = new FileBackedTaskManager();
        FileReader rd = new FileReader(file.getName(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(rd);
        br.readLine();
        while (br.ready()) {
            Task task = fromString(br.readLine());
            if (task != null && task.getType().equals(TypeTask.TASK)) {
                fManager.addNewTask(task);
            } else if (task != null && task.getType().equals(TypeTask.EPIC)) {
                fManager.addNewEpic((Epic)task);
            } else if (task != null && task.getType().equals(TypeTask.SUBTASK)) {
                fManager.addNewSubTask((SubTask)task);
            }
        }
        br.close();
        return fManager;
    }

    //переопределения методов InMemoryTaskManager
    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public int addNewSubTask(SubTask subtask) {
        int id = super.addNewSubTask(subtask);
        save();
        return id;
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
        save();
    }

    @Override
    public void clearAllSubTasks() {
        super.clearAllSubTasks();
        save();
    }

    @Override
    public void updateTask(int id, Task newTask) {
        super.updateTask(id, newTask);
        save();
    }

    @Override
    public void updateEpic(int id, Epic newEpic) {
        super.updateEpic(id, newEpic);
        save();
    }

    @Override
    public void updateSubTask(int id, SubTask newSubTask) {
        super.updateSubTask(id, newSubTask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void setStatusTask(int id, Status status) {
        super.setStatusTask(id, status);
        save();
    }

    @Override
    public void setStatusEpic(int id, Status status) {
        super.setStatusEpic(id, status);
        save();
    }

    @Override
    public void setStatusSubTask(int id, Status status) {
        super.setStatusSubTask(id, status);
        save();
    }
}