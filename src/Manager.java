import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Manager {
    int id = 0;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();;
    HashMap<Integer, SubTask> subTasks = new HashMap<>();;

    ////добавление задач
    public void addNewTask(Task task){
        id++;
        tasks.put(id,task);
    }
    public void addNewEpic(Epic epic){
        id++;
        epics.put(id,epic);
    }
    public void addNewSubTask(SubTask subTask, int idEpic){
        if (epics.containsKey(idEpic)){
            id++;
            subTasks.put(id,subTask);
            epics.get(idEpic).addSubTask(id);
        }
        else{
            System.out.println("Неправильный ввод id эпика");
        }
    }
    ////получение всех задач
    public HashMap<Integer, Task> getTasks(){
        return tasks;
    }
    public HashMap<Integer, Epic> getEpics(){
        return epics;
    }
    public HashMap<Integer, SubTask> getSubTasks(){
        return subTasks;
    }
    ////очищение списка задач
    public void clearAllTasks(){
        this.tasks.clear();
    }
    public void clearAllEpics(){
        this.epics.clear();
    }
    public void clearAllSubTasks(){
        this.subTasks.clear();
    }
    ////получение задачи по id
    public Task getTask(int id){
        return tasks.get(id);
    }
    public Epic getEpic(int id){
        return epics.get(id);
    }
    public SubTask getSubTask(int id){
        return subTasks.get(id);
    }
    ////обновление задачи по id
    public void updateTask(int id, Task newTask){
        if (tasks.containsKey(id)){
            tasks.put(id,newTask);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    public void updateEpic(int id, Epic newEpic){
        if (epics.containsKey(id)){
            epics.put(id,newEpic);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    public void updateSubTask(int id, SubTask newSubTask){
        if (subTasks.containsKey(id)){
            subTasks.put(id,newSubTask);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    ////удаление задачи по id
    public void deleteTask(int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    public void deleteEpic(int id){
        if (epics.containsKey(id)){
            for (int i:epics.get(id).getEpicSubTasks())
                deleteSubTask(i);
            epics.remove(id);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    public void deleteSubTask(int id){
        if (subTasks.containsKey(id)){
            subTasks.remove(id);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    ////получение подзадач конкретного эпика
    public ArrayList<SubTask> getSubTasksOfEpic(int idEpic){
        ArrayList<SubTask> listSubTasks= new ArrayList<>();
        for (int i:epics.get(idEpic).getEpicSubTasks()){
            listSubTasks.add(getSubTask(i));
        }

        return listSubTasks;
    }
    ////установить статус задачи
    public void setStatusTask(int id, String status){
        if (tasks.containsKey(id)){
            tasks.get(id).setStatus(status);
        }
    }
    public boolean isAllSubTasksEqualsStatus(int idEpic, String status){
        boolean flag = true;
        for (SubTask s:getSubTasksOfEpic(idEpic)) {
            if (!s.getStatus().equals(status)) {
                flag = false;
            }
        }
        return flag;
    }
    public void setStatusEpic(int id, String status){
        if (epics.containsKey(id)){
            if(isAllSubTasksEqualsStatus(id, status) && (status.equals("NEW") || status.equals("DONE"))){
                epics.get(id).setStatus(status);
            }
            else {
                epics.get(id).setStatus("IN_PROGRESS");
            }
        }
    }
    public void setStatusSubTask(int id, String status){
        SubTask subTask = subTasks.get(id);
        int idEpic = subTask.getIdEpic();
        if (subTasks.containsKey(id)){
            subTask.setStatus(status);
            setStatusEpic(idEpic, status);
        }
    }

}
