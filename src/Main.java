import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import managers.ManagerSaveException;
import managers.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {

        /*
        try (FileWriter fw = new FileWriter(new File("file.txt"), StandardCharsets.UTF_8)) {
            fw.write("");
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }

        System.out.println("----------Сохранение и загрузка пустого файла--------------------------------------");
        FileBackedTaskManager fileManager1 = FileBackedTaskManager.loadFromFile(new File("file.txt"));
        System.out.println("Задачи:" + fileManager1.getTasks());
        System.out.println("Эпики:" + fileManager1.getEpics());
        System.out.println("Подзадачи:" + fileManager1.getSubTasks());

        System.out.println("\n----------Сохранение задач--------------------------------------");
        fileManager1.addNewTask(new Task("Купить хлеб", "Описание"));
        fileManager1.addNewTask(new Task("Забрать заказ", "Описание"));
        System.out.println("OK");

        System.out.println("\n----------Сохранение эпиков--------------------------------------");
        int idEpic1 = fileManager1.addNewEpic(new Epic("Ремонт", "Описание"));
        int idEpic2 = fileManager1.addNewEpic(new Epic("Переезд", "Описание"));
        System.out.println("OK");

        System.out.println("\n----------Сохранение подзадач--------------------------------------");
        fileManager1.addNewSubTask(new SubTask("Задача 1", "Описание", idEpic1));
        fileManager1.addNewSubTask(new SubTask("Задача 2", "Описание",  idEpic1));
        fileManager1.addNewSubTask(new SubTask("Задача 3", "Описание", idEpic2));
        System.out.println("OK");

        System.out.println("\n----------Загрузка из файла--------------------------------------");
        FileBackedTaskManager fileManager2 = FileBackedTaskManager.loadFromFile(new File("file.txt"));
        System.out.println("Задачи:" + fileManager2.getTasks());
        System.out.println("Эпики:" + fileManager2.getEpics());
        System.out.println("Подзадачи:" + fileManager2.getSubTasks());

        System.out.println("\n----------Удаление эпика 3--------------------------------------");
        fileManager2.deleteEpic(idEpic1);
        System.out.println("Задачи:" + fileManager2.getTasks());
        System.out.println("Эпики:" + fileManager2.getEpics());
        System.out.println("Подзадачи:" + fileManager2.getSubTasks());
        */

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
