package handlers;

import com.sun.net.httpserver.HttpServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EpicsHandlerTest {

    HttpServer httpServer;
    TaskManager tm = Managers.getDefault();

    @BeforeEach
    public void beforeEach() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/epics", new EpicsHandler(tm));
        httpServer.start();
    }
    @AfterEach
    public void afterEach() {
        httpServer.stop(1);
    }

    @Test
    void testHandlePostRequestAddNewEpic() throws IOException,InterruptedException {

        String epicJson = "{ \"name\": \"Epic1\", "+
                            "\"description\": \"Description1\" " +
                          "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Код ответа не совпадает");

        Map<Integer, Epic> epicsFromManager = tm.getEpics();

        assertNotNull(epicsFromManager, "Эпики не возвращаются");
        assertEquals(1, epicsFromManager.size(), "Некорректное количество эпиков");
        assertEquals("Epic1", epicsFromManager.get(1).getName(), "Некорректное имя эпика");

    }

    @Test
    void testHandleGetAllEpics() throws IOException,InterruptedException {

        tm.addNewEpic(new Epic("Epic1", "Desc1"));
        tm.addNewEpic(new Epic("Epic2", "Desc2"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Код ответа не совпадает");

    }

    @Test
    void testHandleDeleteRequestDeleteEpic() throws IOException,InterruptedException {

        int id1 = tm.addNewEpic(new Epic("Epic1", "Desc1"));
        int id2 = tm.addNewEpic(new Epic("Epic2", "Desc2"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/"+id1))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Код ответа не совпадает");
        assertFalse(tm.getEpics().containsKey(id1));
        assertTrue(tm.getEpics().containsKey(id2));

    }

}