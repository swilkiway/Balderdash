package coolness.balderdashserver.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

import coolness.balderdashserver.Model.Answer;
import coolness.balderdashserver.Model.Guess;
import coolness.balderdashserver.Server.ServerFacade;
import coolness.balderdashserver.Server.WebServer;

public class GuessHandler implements HttpHandler {
    public GuessHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        Guess g = gson.fromJson(WebServer.readString(is), Guess.class);
        server.submitGuess(g);
        String response = gson.toJson(g);
        h.sendResponseHeaders(200, 0);
        /*OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();*/
    }
    private ServerFacade server;
}
