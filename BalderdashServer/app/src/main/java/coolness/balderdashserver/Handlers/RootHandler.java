package coolness.balderdashserver.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class RootHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        String path = h.getRequestURI().getPath();
        Scanner sc = new Scanner(path).useDelimiter("/");
        StringBuilder filename = new StringBuilder("C:/web");
        if (sc.hasNext()) {
            while (sc.hasNext()) {
                filename.append("/" + sc.next());
            }
        } else {
            filename.append("/index.html");
        }
        File file = null;
        Scanner file_sc = null;
        int header;
        try {
            file = new File(filename.toString());
            file_sc = new Scanner(new BufferedReader(new FileReader(file)));
            header = 200;
        } catch (FileNotFoundException e) {
            file = new File("C:/web/HTML/404.html");
            file_sc = new Scanner(new BufferedReader(new FileReader(file)));
            header = 404;
            e.printStackTrace();
        }
        StringBuilder file_sb = new StringBuilder();
        while (file_sc.hasNext()) {
            file_sb.append(file_sc.nextLine());
        }
        byte[] my_bytes = file_sb.toString().getBytes();
        h.sendResponseHeaders(header, 0);
        OutputStream resp = h.getResponseBody();
        resp.write(my_bytes);
        resp.close();

    }
}

