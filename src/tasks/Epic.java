package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subTasks = new ArrayList<>();
    public Epic(String name, String description, int id){
        super(name, description, id);
    }
    public void addSubTask(int idSubTask){
        subTasks.add(idSubTask);
    }
    public  ArrayList<Integer> getEpicSubTasks(){
        return subTasks;
    }

    @Override
    public String toString(){
        return name + "(статус=" + status + ") " + subTasks;
    }

}
