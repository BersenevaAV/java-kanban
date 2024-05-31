import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import handlers.*;
import managers.*;

public class HttpTaskServer {

    public static void main(String[] args) throws IOException {

        TaskManager tm = Managers.getDefault();
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TasksHandler(tm));
        httpServer.createContext("/epics", new EpicsHandler(tm));
        httpServer.createContext("/subtasks", new SubtasksHandler(tm));
        httpServer.createContext("/history", new HistoryHandler(tm));
        httpServer.createContext("/prioritized", new PrioritizedHandler(tm));
        httpServer.start();
    }
}