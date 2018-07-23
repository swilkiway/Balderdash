package coolness.balderdashserver.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import coolness.balderdashserver.Model.Question;
import coolness.balderdashserver.Server.ServerFacade;
import coolness.balderdashserver.Server.WebServer;

public class QuestionHandler implements HttpHandler {
    public QuestionHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        Question q = gson.fromJson(WebServer.readString(is), Question.class);
        server.submitQuestion(q);
        String response = gson.toJson(q);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private ServerFacade server;
}

