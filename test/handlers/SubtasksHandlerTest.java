package handlers;

import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubtasksHandlerTest {

    HttpServer httpServer;
    TaskManager tm = Managers.getDefault();

    @BeforeEach
    public void beforeEach() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/subtasks", new SubtasksHandler(tm));
        httpServer.start();
    }
    @AfterEach
    public void afterEach() {
        httpServer.stop(1);
    }

    @Test
    void testHandlePostRequestAddNewSubtask() throws IOException,InterruptedException {

        int id1 = tm.addNewEpic(new Epic("Epic1", "Desc1"));

        String subtaskJson = "{ \"idEpic\": " + id1 + ", " +
                "\"name\": \"Subtask1\", "+
                "\"description\": \"Description Subtask\" " +
                ", \"duration\": \"12\" " +
                ", \"startTime\": \"15.03.2024 14:50:23\"" +
                "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Код ответа не совпадает");

        Map<Integer, SubTask> subtasksManager = tm.getSubTasks();

        assertNotNull(subtasksManager, "Подзадачи не возвращаются");
        assertEquals(1, subtasksManager.size(), "Некорректное количество задач");
        assertEquals("Subtask1", subtasksManager.get(2).getName(), "Некорректное имя подзадачи");

    }


    @Test
    void testHandleGetAllSubtasks() throws IOException,InterruptedException {

        int id1 = tm.addNewEpic(new Epic("Epic1", "Desc1"));
        tm.addNewSubTask(new SubTask("SubTask1", "Desc1", id1));
        tm.addNewSubTask(new SubTask("SubTask2", "Desc2", id1));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
    }


    @Test
    void testHandleDeleteRequestDeleteSubtask() throws IOException,InterruptedException {

        int id1 = tm.addNewEpic(new Epic("Epic1", "Desc1"));
        int idst1 = tm.addNewSubTask(new SubTask("SubTask1", "Desc1", id1));
        int idst2 = tm.addNewSubTask(new SubTask("SubTask2", "Desc2", id1));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/"+idst1))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
        assertFalse(tm.getSubTasks().containsKey(idst1));
        assertTrue(tm.getSubTasks().containsKey(idst2));
    }

}