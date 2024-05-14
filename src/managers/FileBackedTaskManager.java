package managers;
import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{
    String filename = "file.txt";
    public FileBackedTaskManager(){}
    public FileBackedTaskManager(List<Task> allTasks){
        for(Task task: allTasks){
            if(task!=null && task.getType().equals(TypeTask.TASK)){
                super.tasks.put(task.getId(), task);
            }
            else if(task!=null && task.getType().equals(TypeTask.EPIC)){
                super.epics.put(task.getId(), (Epic)task);
            }
            else if(task!=null && task.getType().equals(TypeTask.SUBTASK)){
                super.subTasks.put(task.getId(), (SubTask)task);
            }
        }
    }
    @Override
    public int addNewTask(Task task){
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic){
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public int addNewSubTask(SubTask subtask){
        int id = super.addNewSubTask(subtask);
        save();
        return id;
    }
    public void save() {

        try(FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8)){
            fw.write("id,type,name,status,description,epic" + "\n");
            for(Task task: tasks.values()){
                fw.write(task.toString() + "\n");
            }
            for(Task task: epics.values()){
                fw.write(task.toString() + "\n");
            }
            for(Task task: subTasks.values()){
                fw.write(task.toString() + "\n");
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ManagerSaveException e){
            e.printStackTrace();
        }


    }

    static public Task fromString(String value){
        String[] taskStr = value.split(",");
        Task task;
        if(TypeTask.valueOf(taskStr[1]).equals(TypeTask.TASK)){
            task = new Task(taskStr[2], taskStr[4]);
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));

        }
        else if(TypeTask.valueOf(taskStr[1]).equals(TypeTask.EPIC)){
            task = new Epic(taskStr[2], taskStr[4]);
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));
        }
        else if(TypeTask.valueOf(taskStr[1]).equals(TypeTask.SUBTASK)){
            task = new SubTask(taskStr[2], taskStr[4],Integer.parseInt(taskStr[5]));
            task.setId(Integer.parseInt(taskStr[0]));
            task.setStatus(Status.valueOf(taskStr[3]));
        }
        else{
            task = null;
        }
        return task;
    }

    static public FileBackedTaskManager loadFromFile(File file) throws IOException {
        List<Task> allTasks = new ArrayList<>();
        FileReader rd = new FileReader(file.getName(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(rd);
        br.readLine();
        while(br.ready()){
            allTasks.add(fromString(br.readLine()));
        }
        br.close();
        return new FileBackedTaskManager(allTasks);
    }

}
