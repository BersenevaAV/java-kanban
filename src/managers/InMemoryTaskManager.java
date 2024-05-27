package managers;

import java.time.LocalDateTime;
import java.util.*;

import tasks.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    Set<Task> allTasks = new TreeSet<>();

    private void increaseId() {
        id++;
    }

    ////добавление задач
    @Override
    public int addNewTask(Task task) {
        increaseId();
        tasks.put(this.id, task);
        task.setId(this.id);
        if(task.getStartTime() != null && isCrossing(task) == false) {
            allTasks.add(task);
        }
        return this.id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        increaseId();
        epics.put(id, epic);
        epic.setId(this.id);
        if(epic.getStartTime() != null)
            allTasks.add(epic);
        return this.id;
    }

    @Override
    public int addNewSubTask(SubTask subtask) {
        int idEpic = subtask.getIdEpic();
        if (epics.containsKey(idEpic)) {
            increaseId();
            subTasks.put(id, subtask);
            subtask.setId(this.id);
            getEpic(idEpic).addSubTask(subtask);
            updateEpicStatus(idEpic);
            if(subtask.getStartTime() != null && isCrossing(subtask) == false)
                allTasks.add(subtask);
            return this.id;
        } else {
            System.out.println("Неправильный ввод id эпика");
            return 0;
        }
    }

    ////получение всех задач
    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    ////очищение списка задач
    @Override
    public void clearAllTasks() {
        this.tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        clearAllSubTasks();
        this.epics.clear();
    }

    @Override
    public void clearAllSubTasks() {
        /*for (int i: epics.keySet()) {
            if (!getEpic(i).getEpicSubTasks().isEmpty()) {
                getEpic(i).getEpicSubTasks().clear();
                getEpic(i).setStatus(Status.NEW);
            }
        }*/

        epics.values().stream()
                .filter(epic -> !epic.getEpicSubTasks().isEmpty())
                .map(epic -> {epic.setStatus(Status.NEW); return epic;})
                .map(epic -> {epic.clearSubtasks(); return epic;});
                //.collect(Collectors.toList());
        this.subTasks.clear();
    }

    @Override
    ////получение задачи по id
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else
            return null;
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else
            return null;
    }

    @Override
    public SubTask getSubTask(int id) {
        if (subTasks.containsKey(id)) {
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        } else
            return null;
    }

    @Override
    ////обновление задачи по id
    public void updateTask(int id, Task newTask) {
        if (tasks.containsKey(id)) {
            tasks.put(id,newTask);
            if(newTask.getStartTime() != null && isCrossing(newTask) == false)
                allTasks.add(newTask);
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    @Override
    public void updateEpic(int id, Epic newEpic) {
        if (epics.containsKey(id)) {
            epics.put(id, newEpic);
            if (newEpic.getStartTime() != null && isCrossing(newEpic) == false)
                allTasks.add(newEpic);
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    @Override
    public void updateSubTask(int id, SubTask newSubTask) {
        if (subTasks.containsKey(id)) {
            subTasks.put(id,newSubTask);
            int epicId = getSubTask(id).getIdEpic();
            updateEpicStatus(epicId);
            if(newSubTask.getStartTime() != null && isCrossing(newSubTask) == false)
                allTasks.add(newSubTask);
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    public void updateEpicStatus(int epicId){
        if (isAllSubTasksEqualsStatus(epicId, Status.NEW)) {
            getEpic(epicId).setStatus(Status.NEW);
        } else if (isAllSubTasksEqualsStatus(epicId, Status.DONE)) {
            getEpic(epicId).setStatus(Status.DONE);
        } else {
            getEpic(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

    ////удаление задачи по id
    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            if (!getEpic(id).getEpicSubTasks().isEmpty()) {
                for (int i:getEpic(id).getEpicSubTasks()) {
                    historyManager.remove(i);
                    subTasks.remove(i);
                }
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    @Override
    public void deleteSubTask(int id) {
        if (subTasks.containsKey(id)) {
            int idEpic = getSubTask(id).getIdEpic();
            ArrayList<Integer> subTasksofEpic = getEpic(idEpic).getEpicSubTasks();
            if (subTasksofEpic.contains(id)) {
                getEpic(idEpic).deleteSubTask(id);
                historyManager.remove(id);
            }
            subTasks.remove(id);
            //если у эпика больше нет подзадач, то устанавливаю ему новый статус
            if (subTasksofEpic.isEmpty()) {
                getEpic(idEpic).setStatus(Status.NEW);
            }
        } else {
            System.out.println("Неправильный ввод id");
        }
    }

    ////получение подзадач конкретного эпика
    @Override
    public ArrayList<SubTask> getSubTasksOfEpic(int idEpic) {
        ArrayList<SubTask> listSubTasks = new ArrayList<>();
        for (int i:getEpic(idEpic).getEpicSubTasks()) {
            listSubTasks.add(getSubTask(i));
        }
        return listSubTasks;
    }

    ////установить статус задачи
    public void setStatusTask(int id, Status status) {
        if (tasks.containsKey(id)) {
            getTask(id).setStatus(status);
        }
    }

    public boolean isAllSubTasksEqualsStatus(int idEpic, Status status) {
        boolean flag = true;
        for (SubTask s:getSubTasksOfEpic(idEpic)) {
            if (!s.getStatus().equals(status)) {
                flag = false;
            }
        }
        return flag;
    }

    public void setStatusEpic(int id, Status status) {
        if (epics.containsKey(id)) {
            if (isAllSubTasksEqualsStatus(id, status) && (status.equals(Status.NEW) || status.equals(Status.DONE))) {
                getEpic(id).setStatus(status);
            } else {
                getEpic(id).setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public void setStatusSubTask(int id, Status status) {
        SubTask subTask = getSubTask(id);
        int idEpic = subTask.getIdEpic();
        if (subTasks.containsKey(id)) {
            subTask.setStatus(status);
            setStatusEpic(idEpic, status);
        }
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public Set<Task> getPrioritizedTasks() {
        return allTasks;
    }

    public boolean isCrossing(Task newtask){
        Optional<Task> crossingTasks = allTasks.stream()
                .filter(task -> !(task.getStartTime().isAfter(newtask.getEndTime())
                               || task.getEndTime().isBefore(newtask.getStartTime())
                                 ))
                .findFirst();
        if(crossingTasks.isPresent()){
            System.out.println(crossingTasks);
            return true;
        }
        else
            return false;
    }
}
