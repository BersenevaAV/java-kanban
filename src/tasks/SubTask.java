package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private final int idEpic;

    public SubTask(String name, String description, int idEpic) {
        super(name, description);
        this.idEpic = idEpic;
        super.type = TypeTask.SUBTASK;
    }

    public SubTask(String name, String description, int idEpic, long minutes, LocalDateTime startTime) {
        super(name, description, minutes, startTime);
        this.idEpic = idEpic;
        super.type = TypeTask.SUBTASK;
    }

    public int getIdEpic() {
        return this.idEpic;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + idEpic;
    }
}
