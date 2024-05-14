package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subTasks = new ArrayList<>();
    public Epic(String name, String description){
        super(name, description);
        super.type = TypeTask.EPIC;
    }
    public void addSubTask(int idSubTask){
        subTasks.add(idSubTask);
    }
    public  ArrayList<Integer> getEpicSubTasks(){
        return subTasks;
    }

    @Override
    public String toString(){
        return id + "," + type + "," + name + "," + status + "," + description;
    }

}
