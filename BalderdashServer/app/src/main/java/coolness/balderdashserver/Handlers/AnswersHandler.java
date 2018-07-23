package coolness.balderdashserver.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Server.ServerFacade;

public class AnswersHandler implements HttpHandler {
    public AnswersHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        Gson gson = new Gson();
        Answer[] a = server.getAnswers();
        String response = gson.toJson(a);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private ServerFacade server;
}

