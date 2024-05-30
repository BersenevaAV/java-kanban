package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EpicsHandler extends BaseHttpHandler {

    public EpicsHandler(TaskManager tm) {
        super(tm);
    }

    protected void handleGetRequest(HttpExchange httpExchange) throws IOException {

        String path = httpExchange.getRequestURI().getPath();
        String[] wayElements = path.split("/");

        if (wayElements.length == 2) {
            sendText(httpExchange, tm.getEpics().toString());
        } else if (wayElements.length == 3) {
            int id = Integer.parseInt(wayElements[2]);
            if (tm.getEpics().containsKey(id)) {
                sendText(httpExchange, tm.getEpic(id).toString());
            } else {
                sendNotFound(httpExchange, "Not Found");
            }
        } else if (wayElements.length == 4 && wayElements[3].equals("subtasks")) {
            int id = Integer.parseInt(wayElements[2]);
            if (tm.getEpics().containsKey(id)) {
                httpExchange.sendResponseHeaders(200, 0);
                List<SubTask> subtasks = new ArrayList<>();
                for (int i:tm.getEpic(id).getEpicSubTasks()) {
                    subtasks.add(tm.getSubTask(i));
                }
                sendText(httpExchange, subtasks.toString());
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

        Gson gson = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Epic epic = gson.fromJson(body, Epic.class);

        if (epic.getStatus() == null) {
            epic.setStatus(Status.NEW);
        }

        if (epic.getId() == 0)
            tm.addNewEpic(epic);
        else
            tm.updateEpic(epic.getId(), epic);

        httpExchange.sendResponseHeaders(201, 0);
        httpExchange.getResponseBody().write("".getBytes());
        httpExchange.close();
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
