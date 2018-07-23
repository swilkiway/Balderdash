package coolness.balderdashserver.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

import coolness.balderdashserver.Handlers.AnswerHandler;
import coolness.balderdashserver.Handlers.AnswersHandler;
import coolness.balderdashserver.Handlers.GuessHandler;
import coolness.balderdashserver.Handlers.NextHandler;
import coolness.balderdashserver.Handlers.QuestionHandler;
import coolness.balderdashserver.Handlers.ReadyHandler;
import coolness.balderdashserver.Handlers.RegisterHandler;
import coolness.balderdashserver.Handlers.RootHandler;

public class WebServer {
    public static void main(String args[]) throws IOException {
        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), port);
        server.createContext("/",new RootHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/submitquestion", new QuestionHandler());
        server.createContext("/getnextquestion", new NextHandler());
        server.createContext("/submitanswer", new AnswerHandler());
        server.createContext("/submitguess", new GuessHandler());
        server.createContext("/getanswers", new AnswersHandler());
        server.createContext("/testready", new ReadyHandler());
        server.setExecutor(null);
        server.start();
    }
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}

