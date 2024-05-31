package handlers;

import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TasksHandlerTest {

    HttpServer httpServer;
    TaskManager tm = Managers.getDefault();

    @BeforeEach
    public void beforeEach() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TasksHandler(tm));
        httpServer.start();
    }
    @AfterEach
    public void afterEach() {
        httpServer.stop(1);
    }


    @Test
    void testHandlePostRequestAddNewTask() throws IOException,InterruptedException {

        String taskJson = "{ \"name\": \"Task1\", "+
                "\"description\": \"Description1\" " +
                ", \"duration\": \"12\" " +
                ", \"startTime\": \"15.03.2024 14:50:23\"" +
                "}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        Map<Integer, Task> tasksFromManager = tm.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Task1", tasksFromManager.get(1).getName(), "Некорректное имя задачи");

    }

    @Test
    void testHandleGetAllTasks() throws IOException,InterruptedException {
        tm.addNewTask(new Task("Task1", "Desc1"));
        tm.addNewTask(new Task("Task2", "Desc2"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
    }


    @Test
    void testHandleDeleteRequestDeleteTask() throws IOException,InterruptedException {

        int id1 = tm.addNewTask(new Task("Task1", "Desc1"));
        int id2 = tm.addNewTask(new Task("Task2", "Desc2"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/"+id1))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
        assertFalse(tm.getTasks().containsKey(id1));
        assertTrue(tm.getTasks().containsKey(id2));
    }

}