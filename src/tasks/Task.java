package tasks;

public class Task {
    protected int id;;
    protected String name;
    protected String description;
    protected Status status;
    protected TypeTask type;
    public Task(String name, String description){
        this.name = name;
        this.description = description;
        this.id = 0;
        this.status = Status.NEW;
        this.type = TypeTask.TASK;
    }
    
    public TypeTask getType() {
        return type;
    }

    public void setStatus(Status status){
        if (status.equals(Status.NEW) || status.equals(Status.IN_PROGRESS) || status.equals(Status.DONE))
            this.status = status;
    }
    public Status getStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return id + "," + type + "," + name + "," + status + "," + description;

    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        if (this.id == 0)
            this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        return obj != null && obj.getClass() == this.getClass() && ((Task) obj).getId()==this.id && this.id!=0;
    }


}
