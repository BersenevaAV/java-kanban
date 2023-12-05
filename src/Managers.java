public class Managers {
    private TaskManager taskManager = new InMemoryTaskManager();
    static HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager getDefault(){
        return taskManager;
    }
    static HistoryManager getDefaultHistory(){
        return historyManager;
    }
}
