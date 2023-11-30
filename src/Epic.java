import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Integer> subTasks = new ArrayList<>();
    Epic(String name, String description){
        super(name, description);
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
