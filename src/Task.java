public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected StatusesOfTasks status = StatusesOfTasks.NEW;
    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setStatus(StatusesOfTasks status){
        if (status.equals(StatusesOfTasks.NEW) || status.equals(StatusesOfTasks.IN_PROGRESS) || status.equals(StatusesOfTasks.DONE))
            this.status = status;
    }
    public StatusesOfTasks getStatus(){
        return this.status;
    }
    @Override
    public String toString(){
        return name + "(статус=" + status + ")";
    }



}
