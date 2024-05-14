import managers.FileBackedTaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("----------Сохранение и загрузка пустого файла--------------------------------------");
        FileBackedTaskManager fileManager1 = new FileBackedTaskManager();
        fileManager1.save();
        fileManager1.loadFromFile(new File("file.txt"));
        System.out.println("Задачи:" + fileManager1.getTasks());
        System.out.println("Эпики:" + fileManager1.getEpics());
        System.out.println("Подзадачи:" + fileManager1.getSubTasks());

        System.out.println("\n----------Сохранение задач--------------------------------------");
        fileManager1.addNewTask(new Task("Купить хлеб", "Описание"));
        fileManager1.addNewTask(new Task("Забрать заказ", "Описание"));
        System.out.println("OK");

        System.out.println("\n----------Сохранение эпиков--------------------------------------");
        fileManager1.addNewEpic(new Epic("Ремонт", "Описание"));
        fileManager1.addNewEpic(new Epic("Переезд", "Описание"));
        System.out.println("OK");

        System.out.println("\n----------Сохранение подзадач--------------------------------------");
        fileManager1.addNewSubTask(new SubTask("Задача 1", "Описание", 3));
        fileManager1.addNewSubTask(new SubTask("Задача 2", "Описание",  3));
        fileManager1.addNewSubTask(new SubTask("Задача 3", "Описание", 3));
        System.out.println("OK");

        System.out.println("\n----------Загрузка из файла--------------------------------------");
        FileBackedTaskManager fileManager2 = new FileBackedTaskManager();
        fileManager2 = fileManager2.loadFromFile(new File("file.txt"));
        System.out.println("Задачи:" + fileManager2.getTasks());
        System.out.println("Эпики:" + fileManager2.getEpics());
        System.out.println("Подзадачи:" + fileManager2.getSubTasks());


    }

}
