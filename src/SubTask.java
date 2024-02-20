public class SubTask extends Task{
    private final int idEpic;
    SubTask(String name, String description, int idEpic, int id){
        super(name, description, id);
        this.idEpic=idEpic;
    }
    public int getIdEpic(){
        return this.idEpic;
    }
}
