import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Manager manager = new Manager();
        manager.addNewTask(new Task("Купить хлеб", ""));
        manager.addNewTask(new Task("Забрать заказ", ""));

        manager.addNewEpic(new Epic("Ремонт", ""));
        manager.addNewEpic(new Epic("Переезд", ""));

        manager.addNewSubTask(new SubTask("Задача 1", "", 3),3);
        manager.addNewSubTask(new SubTask("Задача 2", "",  3),3);
        manager.addNewSubTask(new SubTask("Задача 1", "", 4),4);

        System.out.println("----------Проверка вывода--------------------------------------");
        System.out.println("Задачи:  " + manager.getTasks());
        System.out.println("Эпики:  " + manager.getEpics());
        System.out.println("Подзадачи:  " + manager.getSubTasks());

        System.out.println("----------Проверка статуса--------------------------------------");
        manager.setStatusSubTask(5, "DONE");
        manager.setStatusSubTask(7, "DONE");
        System.out.println("Эпики:  " + manager.getEpics());
        manager.setStatusTask(1, "IN_PROGRESS");
        System.out.println("Задачи:  " + manager.getTasks());

        System.out.println("----------Проверка удаления задач--------------------------------------");
        manager.deleteTask(1);
        System.out.println("Задачи:  " + manager.getTasks());
        manager.deleteEpic(3);
        System.out.println("Эпики:  " + manager.getEpics());
        System.out.println("Подзадачи:  " + manager.getSubTasks());
    }

}
