import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException {
        InMemoryTaskManager tm = new InMemoryTaskManager();
        tm.addNewTask(new Task("Купить хлеб", "Описание", 10, LocalDateTime.now()));
        tm.addNewTask(new Task("Забрать заказ", "Описание", 15, LocalDateTime.now().plus(Duration.ofMinutes(20))));
        System.out.println(tm.getPrioritizedTasks());
        int idEpic1 = tm.addNewEpic(new Epic("Ремонт", "Описание"));
        tm.addNewSubTask(new SubTask("Задача 1", "Описание", idEpic1, 100, LocalDateTime.now().plus(Duration.ofMinutes(150))));
        tm.addNewSubTask(new SubTask("Задача 2", "Описание", idEpic1, 150, LocalDateTime.now().plus(Duration.ofMinutes(350))));
        System.out.println(tm.getPrioritizedTasks());
    }
}
