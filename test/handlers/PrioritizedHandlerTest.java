package handlers;

import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PrioritizedHandlerTest {
    HttpServer httpServer;
    TaskManager tm = Managers.getDefault();

    @BeforeEach
    public void beforeEach() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/prioritized", new PrioritizedHandler(tm));
        httpServer.start();
    }

    @AfterEach
    public void afterEach() {
        httpServer.stop(1);
    }

    @Test
    void testHandleGetPrioritized() throws IOException,InterruptedException {

        LocalDateTime ldt = LocalDateTime.now();
        tm.addNewTask(new  Task("Test1", "description1", 5, ldt));
        tm.addNewTask(new  Task("Test2", "description2", 5, ldt.plus(Duration.ofMinutes(15))));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/prioritized"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertFalse(tm.getPrioritizedTasks().isEmpty(), "Приоритетный список пуст");
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
    }
}