package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task implements Comparable<Task>{
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TypeTask type;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = 0;
        this.status = Status.NEW;
        this.type = TypeTask.TASK;
    }

    public Task(String name, String description, long minutes, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = 0;
        this.status = Status.NEW;
        this.type = TypeTask.TASK;
        this.duration = Duration.ofMinutes(minutes);
        this.startTime = startTime;
    }

    public TypeTask getType() {
        return type;
    }

    public void setStatus(Status status) {
        if (status.equals(Status.NEW) || status.equals(Status.IN_PROGRESS) || status.equals(Status.DONE))
            this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0)
            this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && ((Task) obj).getId() == this.id && this.id != 0;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public int compareTo(Task task) {
        if (this.startTime.isAfter(task.getStartTime()))
            return 1;
        else if(this.startTime.equals(task.getStartTime()))
            return 0;
        else
            return -1;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
