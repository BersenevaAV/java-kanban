import managers.InMemoryTaskManager;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        System.out.println("\n----------Добавление задач--------------------------------------");
        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.addNewTask("Купить хлеб", "");
        taskManager.addNewTask("Забрать заказ", "");

        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("\n----------Добавление эпиков--------------------------------------");
        taskManager.addNewEpic("Ремонт", "");
        taskManager.addNewEpic("Переезд", "");

        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("\n----------Добавление подзадач--------------------------------------");
        taskManager.addNewSubTask("Задача 1", "", 3);
        taskManager.addNewSubTask("Задача 2", "",  3);
        taskManager.addNewSubTask("Задача 3", "", 3);

        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("\n----------Проверка вывода--------------------------------------");
        System.out.println("Задачи:  " + taskManager.getTasks());
        System.out.println("Эпики:  " + taskManager.getEpics());
        System.out.println("Подзадачи:  " + taskManager.getSubTasks());

        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("---------------------------------------------------------------");
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getSubTask(6));
        System.out.println(taskManager.getEpic(4));
        System.out.println(taskManager.getEpic(3));
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getSubTask(7));
        System.out.println(taskManager.getSubTask(5));

        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("\n----------Проверка удаления задач--------------------------------------");
        taskManager.deleteTask(1);
        System.out.println("Задачи:  " + taskManager.getTasks());

        taskManager.deleteSubTask(6);
        System.out.println("История задач:" + taskManager.getHistory());

        System.out.println("\n----------Проверка удаления эпика--------------------------------------");

        taskManager.deleteEpic(3);
        System.out.println("История задач:" + taskManager.getHistory());

        taskManager.deleteEpic(4);
        System.out.println("История задач:" + taskManager.getHistory());
    }

}
