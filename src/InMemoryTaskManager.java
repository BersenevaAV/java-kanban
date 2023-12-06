import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0; //если сделать final, то не смогу присваивать новый id для каждой новой задачи
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    //private HistoryManager historyManager= new InMemoryHistoryManager();
    private final HistoryManager historyManager= Managers.getDefaultHistory();


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
    public Map<Integer, Task> getTasks(){
        return tasks;
    }
    @Override
    public Map<Integer, Epic> getEpics(){
        return epics;
    }
    @Override
    public Map<Integer, SubTask> getSubTasks(){
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
                getEpic(i).setStatus(Status.NEW);
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
            if(isAllSubTasksEqualsStatus(id, Status.NEW)){
                getEpic(epicId).setStatus(Status.NEW);
            }
            else if(isAllSubTasksEqualsStatus(id, Status.DONE)){
                getEpic(epicId).setStatus(Status.DONE);
            }
            else {
                getEpic(epicId).setStatus(Status.IN_PROGRESS);
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
                getEpic(idEpic).setStatus(Status.NEW);
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
    public void setStatusTask(int id, Status status){
        if (tasks.containsKey(id)){
            getTask(id).setStatus(status);
        }
    }
    public boolean isAllSubTasksEqualsStatus(int idEpic, Status status){
        boolean flag = true;
        for (SubTask s:getSubTasksOfEpic(idEpic)) {
            if (!s.getStatus().equals(status)) {
                flag = false;
            }
        }
        return flag;
    }
    @Override
    public void setStatusEpic(int id, Status status){
        if (epics.containsKey(id)){
            if(isAllSubTasksEqualsStatus(id, status) && (status.equals(Status.NEW) || status.equals(Status.DONE))){
                getEpic(id).setStatus(status);
            }
            else {
                getEpic(id).setStatus(Status.IN_PROGRESS);
            }
        }
    }
    @Override
    public void setStatusSubTask(int id, Status status){
        SubTask subTask = getSubTask(id);
        int idEpic = subTask.getIdEpic();
        if (subTasks.containsKey(id)){
            subTask.setStatus(status);
            setStatusEpic(idEpic, status);
        }
    }
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}
