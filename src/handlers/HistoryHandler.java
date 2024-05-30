package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {

    TaskManager tm;
    public HistoryHandler(TaskManager tm) {
        this.tm = tm;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String response = "";

        switch(method) {
            case "GET":
                response = handleGetHistoryRequest(httpExchange);
                break;
            default:
                response = "Некорректный метод!";
        }

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private String handleGetHistoryRequest(HttpExchange httpExchange) throws IOException {

        String path = httpExchange.getRequestURI().getPath();
        String[] wayElements = path.split("/");

        if (wayElements.length == 2) {
            httpExchange.sendResponseHeaders(200, 0);
            return tm.getHistory().toString();
        }
        else {
            httpExchange.sendResponseHeaders(500, 0);
            return "Internal Server Error";
        }
    }
}
