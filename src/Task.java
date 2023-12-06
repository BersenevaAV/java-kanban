public class Task {
    protected int id;
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



}
