package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.Task;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class TasksHandler extends BaseHttpHandler {

    public TasksHandler(TaskManager tm) {
        super(tm);
    }

    @Override
    protected void handleGetRequest(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] wayElements = path.split("/");

        if (wayElements.length == 2) {
            sendText(httpExchange, tm.getTasks().toString());
        } else if (wayElements.length == 3) {
            int id = Integer.parseInt(wayElements[2]);
            if (tm.getTasks().containsKey(id)) {
                sendText(httpExchange, tm.getTask(id).toString());
            } else {
                sendNotFound(httpExchange, "Not Found");
            }
        } else {
            httpExchange.sendResponseHeaders(500, 0);
            httpExchange.getResponseBody().write("Internal Server Error".getBytes());
            httpExchange.close();
        }
    }

    @Override
    protected void handlePostRequest(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Task task = gson.fromJson(body, Task.class);

        String response = "";
        if (task.getStartTime() != null && tm.isCrossing(task) == true) {
            sendHasInteractions(httpExchange, "Not Acceptable");
        } else {
            httpExchange.sendResponseHeaders(201, 0);
            httpExchange.getResponseBody().write("".getBytes());
            httpExchange.close();
        }

        if (task.getId() == 0)
            tm.addNewTask(task);
        else
            tm.updateTask(task.getId(), task);
    }

    @Override
    protected void handleDeleteRequest(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Task task = gson.fromJson(body, Task.class);
        tm.deleteTask(task.getId());

        sendText(httpExchange, "");
    }
}
