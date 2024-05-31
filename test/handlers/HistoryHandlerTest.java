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

import static org.junit.jupiter.api.Assertions.*;

class HistoryHandlerTest {
    HttpServer httpServer;
    TaskManager tm = Managers.getDefault();

    @BeforeEach
    public void beforeEach() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/history", new HistoryHandler(tm));
        httpServer.start();
    }

    @AfterEach
    public void afterEach() {
        httpServer.stop(1);
    }

    @Test
    void testHandleGetHistory() throws IOException,InterruptedException {

        int id1 = tm.addNewEpic(new Epic("Epic1", "Desc1"));
        tm.addNewSubTask(new SubTask("SubTask1", "Desc1", id1));
        tm.addNewSubTask(new SubTask("SubTask2", "Desc2", id1));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/history"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertFalse(tm.getHistory().isEmpty(), "История пуста");
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");

    }
}