package coolness.balderdashserver.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import coolness.balderdashserver.Model.ReadyRequest;
import coolness.balderdashserver.Model.ReadyResponse;
import coolness.balderdashserver.Server.ServerFacade;
import coolness.balderdashserver.Server.WebServer;

public class ReadyHandler implements HttpHandler {
    public ReadyHandler() { server = new ServerFacade(); }
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        ReadyRequest r = gson.fromJson(WebServer.readString(is), ReadyRequest.class);
        ReadyResponse rr = server.testReady(r);
        String response = gson.toJson(rr);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private ServerFacade server;
}

