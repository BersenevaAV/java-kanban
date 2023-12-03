public class SubTask extends Task{
    private final int idEpic;
    SubTask(String name, String description, int idEpic){
        super(name, description);
        this.idEpic=idEpic;
    }
    public int getIdEpic(){
        return this.idEpic;
    }
}
