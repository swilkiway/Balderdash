package coolness.balderdashserver.Handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import coolness.balderdashserver.Server.ServerFacade;
import coolness.balderdashserver.Model.User;
import coolness.balderdashserver.Server.WebServer;

public class RegisterHandler implements HttpHandler {
    public RegisterHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        User u = gson.fromJson(WebServer.readString(is), User.class);
        server.register(u);
        String response = gson.toJson(u);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private ServerFacade server;
}

