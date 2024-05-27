package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, SubTask> subtasks;
    LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        super.type = TypeTask.EPIC;
        this.subtasks = new HashMap<>();
    }

    public void addSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(),subTask);
        setTime();
    }

    public ArrayList<Integer> getEpicSubTasks() {
        return new ArrayList<>(subtasks.keySet());
    }

    public void clearSubtasks() {
        subtasks.clear();
        startTime = null;
        endTime = null;
        duration = null;
    }

    public void deleteSubTask(int idSubtask) {
        subtasks.remove(idSubtask);
        setTime();
    }

    public void setTime() {
        for (SubTask s: subtasks.values()) {
            if (s.getStartTime() != null) {
                if (this.startTime == null)
                    this.startTime = s.getStartTime();
                else {
                    if (this.startTime.isAfter(s.getStartTime()))
                        this.startTime = s.getStartTime();
                }
                if (this.endTime == null)
                    this.endTime = s.getEndTime();
                else {
                    if (this.endTime.isBefore(s.getEndTime()))
                        this.endTime = s.getEndTime();
                }
                if (this.startTime != null && this.endTime != null)
                    this.duration = Duration.between(startTime,endTime);
            }
        }
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

}
