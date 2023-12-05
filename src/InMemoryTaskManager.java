import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0; //если сделать final, то не смогу присваивать новый id для каждой новой задачи
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HistoryManager historyManager= new InMemoryHistoryManager();


    private void increaseId(){
        id++;
    }
    ////добавление задач
    @Override
    public void addNewTask(Task task){
        increaseId();
        tasks.put(id,task);
    }
    @Override
    public void addNewEpic(Epic epic){
        increaseId();
        epics.put(id,epic);
    }
    @Override
    public void addNewSubTask(SubTask subTask, int idEpic){
        if (epics.containsKey(idEpic)){
            increaseId();
            subTasks.put(id,subTask);
            getEpic(idEpic).addSubTask(id);
        }
        else{
            System.out.println("Неправильный ввод id эпика");
        }
    }
    ////получение всех задач
    @Override
    public HashMap<Integer, Task> getTasks(){
        return tasks;
    }
    @Override
    public HashMap<Integer, Epic> getEpics(){
        return epics;
    }
    @Override
    public HashMap<Integer, SubTask> getSubTasks(){
        return subTasks;
    }
    ////очищение списка задач
    @Override
    public void clearAllTasks(){
        this.tasks.clear();
    }
    @Override
    public void clearAllEpics(){
        clearAllSubTasks();
        this.epics.clear();
    }
    @Override
    public void clearAllSubTasks(){
        for (int i: epics.keySet()){
            if(!getEpic(i).getEpicSubTasks().isEmpty()){
                getEpic(i).getEpicSubTasks().clear();
                getEpic(i).setStatus(StatusesOfTasks.NEW);
            }
        }
        this.subTasks.clear();
    }
    @Override
    ////получение задачи по id
    public Task getTask(int id){
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }
    @Override
    public Epic getEpic(int id)
    {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    @Override
    public SubTask getSubTask(int id){
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }
    @Override
    ////обновление задачи по id
    public void updateTask(int id, Task newTask){
        if (tasks.containsKey(id)){
            tasks.put(id,newTask);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    @Override
    public void updateEpic(int id, Epic newEpic){
        if (epics.containsKey(id)){
            epics.put(id,newEpic);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    @Override
    public void updateSubTask(int id, SubTask newSubTask){
        if (subTasks.containsKey(id)){
            subTasks.put(id,newSubTask);
            int epicId = getSubTask(id).getIdEpic();
            if(isAllSubTasksEqualsStatus(id, StatusesOfTasks.NEW)){
                getEpic(epicId).setStatus(StatusesOfTasks.NEW);
            }
            else if(isAllSubTasksEqualsStatus(id, StatusesOfTasks.DONE)){
                getEpic(epicId).setStatus(StatusesOfTasks.DONE);
            }
            else {
                getEpic(epicId).setStatus(StatusesOfTasks.IN_PROGRESS);
            }
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    ////удаление задачи по id
    @Override
    public void deleteTask(int id){
        if (tasks.containsKey(id)){
            tasks.remove(id);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    @Override
    public void deleteEpic(int id){
        if (epics.containsKey(id)){
            for (int i:getEpic(id).getEpicSubTasks())
                deleteSubTask(i);
            epics.remove(id);
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    @Override
    public void deleteSubTask(int id){

        if (subTasks.containsKey(id)){
            //если у эпика больше нет подзадач, то устанавливаю ему новый статус
            int idEpic = getSubTask(id).getIdEpic();
            ArrayList<Integer> subTasksofEpic = getEpic(idEpic).getEpicSubTasks();
            subTasks.remove(id);
            if(subTasksofEpic.contains(id)){
                subTasksofEpic.remove((Object)id);
            }
            if(subTasksofEpic.isEmpty()){
                getEpic(idEpic).setStatus(StatusesOfTasks.NEW);
            }
        }
        else{
            System.out.println("Неправильный ввод id");
        }
    }
    ////получение подзадач конкретного эпика
    @Override
    public ArrayList<SubTask> getSubTasksOfEpic(int idEpic){
        ArrayList<SubTask> listSubTasks= new ArrayList<>();
        for (int i:getEpic(idEpic).getEpicSubTasks()){
            listSubTasks.add(getSubTask(i));
        }
        return listSubTasks;
    }
    ////установить статус задачи
    @Override
    public void setStatusTask(int id, StatusesOfTasks status){
        if (tasks.containsKey(id)){
            getTask(id).setStatus(status);
        }
    }
    public boolean isAllSubTasksEqualsStatus(int idEpic, StatusesOfTasks status){
        boolean flag = true;
        for (SubTask s:getSubTasksOfEpic(idEpic)) {
            if (!s.getStatus().equals(status)) {
                flag = false;
            }
        }
        return flag;
    }
    @Override
    public void setStatusEpic(int id, StatusesOfTasks status){
        if (epics.containsKey(id)){
            if(isAllSubTasksEqualsStatus(id, status) && (status.equals(StatusesOfTasks.NEW) || status.equals(StatusesOfTasks.DONE))){
                getEpic(id).setStatus(status);
            }
            else {
                getEpic(id).setStatus(StatusesOfTasks.IN_PROGRESS);
            }
        }
    }
    @Override
    public void setStatusSubTask(int id, StatusesOfTasks status){
        SubTask subTask = getSubTask(id);
        int idEpic = subTask.getIdEpic();
        if (subTasks.containsKey(id)){
            subTask.setStatus(status);
            setStatusEpic(idEpic, status);
        }
    }
    public List<Task> getListHistory(){
        return historyManager.getHistory();
    }
}
