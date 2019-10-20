package server;

import data.BaseRequest;
import data.PostRequest;
import exception.HttpfsException;
import services.RequestParsingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static constants.Constants.POST;

public class HttpServer {

    private HttpServer() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start() throws HttpfsException {
        try {
            // TODO Port from parsed data
            SocketAddress bindAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080);
            ServerSocket server = new ServerSocket();

            server.bind(bindAddress);

            // Server runs until terminated
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                BaseRequest request = RequestParsingService.parseRequest(in);


                System.err.print(request.getMethod() + " ");
                System.err.print(request.getHttpVersion() + " ");
                System.err.println(request.getUri() + " ");
                request.getHeaders().forEach((k, v) -> System.err.println(k + ": " + v));

                System.out.println("\n");
                if (request.getMethod().equalsIgnoreCase(POST)) {
                    System.err.println(((PostRequest) request).getBody());
                }

                String body = "Im sending this from httpfs. Hello.";
                out.println("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + body.length() + "\r\n\r\n" + body);

                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            throw new HttpfsException("Address/Port combination not valid");
        }

    }
}
