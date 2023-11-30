public class Task {
    String name;
    String description;
    int id = 0;
    String status = "NEW";
    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }
    public void setStatus(String status){
        if (status.equals("NEW") || status.equals("IN_PROGRESS") || status.equals("DONE"))
            this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    @Override
    public String toString(){
        return name + "(статус=" + status + ")";
    }



}
