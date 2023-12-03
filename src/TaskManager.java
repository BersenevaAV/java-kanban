import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0; //если сделать final, то не смогу присваивать новый id для каждой новой задачи
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private void increaseId(){
        id++;
    }
    ////добавление задач
    public void addNewTask(Task task){
        increaseId();
        tasks.put(id,task);
    }
    public void addNewEpic(Epic epic){
        increaseId();
        epics.put(id,epic);
    }
    public void addNewSubTask(SubTask subTask, int idEpic){
        if (epics.containsKey(idEpic)){
            increaseId();
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
        clearAllSubTasks();
        this.epics.clear();
    }
    public void clearAllSubTasks(){
        for (int i: epics.keySet()){
            if(!epics.get(i).getEpicSubTasks().isEmpty()){
                epics.get(i).getEpicSubTasks().clear();
                epics.get(i).setStatus("NEW");
            }
        }
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
            int epicId = subTasks.get(id).getIdEpic();
            if(isAllSubTasksEqualsStatus(id, "NEW")){
                epics.get(epicId).setStatus("NEW");
            }
            else if(isAllSubTasksEqualsStatus(id, "DONE")){
                epics.get(epicId).setStatus("DONE");
            }
            else {
                epics.get(epicId).setStatus("IN_PROGRESS");
            }
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
            //если у эпика больше нет подзадач, то устанавливаю ему новый статус
            int idEpic = subTasks.get(id).getIdEpic();
            ArrayList<Integer> subTasksofEpic = epics.get(idEpic).getEpicSubTasks();
            subTasks.remove(id);
            if(subTasksofEpic.contains(id)){
                subTasksofEpic.remove((Object)id);
            }
            if(subTasksofEpic.isEmpty()){
                epics.get(idEpic).setStatus("NEW");
            }
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
