package tasks;

public class Task {
    protected int id=0;
    protected String name;
    protected String description;
    protected Status status = Status.NEW;
    public Task(String name, String description){
        this.name = name;
        this.description = description;
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
        return name + "(статус=" + status + ")";
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
