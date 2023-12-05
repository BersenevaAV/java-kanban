import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.addNewTask(new Task("Купить хлеб", ""));
        taskManager.addNewTask(new Task("Забрать заказ", ""));

        taskManager.addNewEpic(new Epic("Ремонт", ""));
        taskManager.addNewEpic(new Epic("Переезд", ""));

        taskManager.addNewSubTask(new SubTask("Задача 1", "", 3),3);
        taskManager.addNewSubTask(new SubTask("Задача 2", "",  3),3);
        taskManager.addNewSubTask(new SubTask("Задача 1", "", 4),4);

        System.out.println("----------Проверка вывода--------------------------------------");
        System.out.println("Задачи:  " + taskManager.getTasks());
        System.out.println("Эпики:  " + taskManager.getEpics());
        System.out.println("Подзадачи:  " + taskManager.getSubTasks());

        System.out.println("----------Проверка статуса--------------------------------------");
        taskManager.setStatusSubTask(5, StatusesOfTasks.DONE);
        taskManager.setStatusSubTask(7, StatusesOfTasks.DONE);
        System.out.println("Эпики:  " + taskManager.getEpics());
        taskManager.setStatusTask(1, StatusesOfTasks.IN_PROGRESS);
        System.out.println("Задачи:  " + taskManager.getTasks());

        System.out.println("----------Проверка удаления задач--------------------------------------");
        taskManager.deleteTask(1);
        System.out.println("Задачи:  " + taskManager.getTasks());

        taskManager.deleteEpic(3);
        System.out.println("Эпики:  " + taskManager.getEpics());
        System.out.println("Подзадачи:  " + taskManager.getSubTasks());

        System.out.println("----------Проверка удаления всех задач--------------------------------------");
        taskManager.clearAllSubTasks();
        System.out.println("Эпики:  " + taskManager.getEpics());
        System.out.println("Подзадачи:  " + taskManager.getSubTasks());

        System.out.println("----------Проверка историии --------------------------------------");

        System.out.println(((InMemoryTaskManager) taskManager).getListHistory());

    }

}
