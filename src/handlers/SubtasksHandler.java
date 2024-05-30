package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import handlers.BaseHttpHandler;
import handlers.DurationAdapter;
import handlers.LocalDateTimeAdapter;
import managers.TaskManager;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubtasksHandler extends BaseHttpHandler {

    public SubtasksHandler(TaskManager tm) {
        super(tm);
    }

    protected void handleGetRequest(HttpExchange httpExchange) throws IOException {

        String path = httpExchange.getRequestURI().getPath();
        String[] wayElements = path.split("/");

        if (wayElements.length == 2) {
            sendText(httpExchange, tm.getSubTasks().toString());
        } else if (wayElements.length == 3) {
            int id = Integer.parseInt(wayElements[2]);
            if (tm.getSubTasks().containsKey(id)) {
                sendText(httpExchange, tm.getSubTask(id).toString());
            } else {
                sendNotFound(httpExchange, "Not Found");
            }
        } else {
            httpExchange.sendResponseHeaders(500, 0);
            httpExchange.getResponseBody().write("Internal Server Error".getBytes());
            httpExchange.close();
        }
    }


    protected void handlePostRequest(HttpExchange httpExchange) throws IOException {

        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(body);
        Gson gson = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        SubTask subtask = gson.fromJson(body, SubTask.class);

        String response = "";
        if (subtask.getStartTime() != null && tm.isCrossing(subtask) == true) {
            sendHasInteractions(httpExchange, "Not Acceptable");
        } else {
            httpExchange.sendResponseHeaders(201, 0);
            httpExchange.getResponseBody().write("".getBytes());
            httpExchange.close();
        }

        if (subtask.getStatus() == null) {
            subtask.setStatus(Status.NEW);
        }
        if (subtask.getId() == 0)
            tm.addNewSubTask(subtask);
        else
            tm.updateSubTask(subtask.getId(), subtask);
    }

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

