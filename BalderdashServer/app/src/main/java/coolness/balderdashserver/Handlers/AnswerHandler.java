package coolness.balderdashserver.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Server.ServerFacade;
import coolness.balderdashserver.Server.WebServer;

public class AnswerHandler implements HttpHandler {
    public AnswerHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        Answer a = gson.fromJson(WebServer.readString(is), Answer.class);
        server.submitAnswer(a);
        String response = gson.toJson(a);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private ServerFacade server;
}
