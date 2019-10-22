package server;

import data.BaseRequest;
import data.GetRequest;
import data.PostRequest;
import data.Response;
import exception.HttpfsException;
import services.GetFileService;
import services.PostFileService;
import services.RequestParsingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static constants.Constants.GET;

public class HttpServer {

    private HttpServer() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start() throws HttpfsException {
        try {
            // TODO overhaul this exception stuff, it should all be RESPONSES
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

                Response response;
                if (request.getMethod().equalsIgnoreCase(GET)) {
                    response = GetFileService.handleRequest((GetRequest) request);
                } else {
                    response = PostFileService.handleRequest((PostRequest) request);
                }

                out.println(response);
//                out.println("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + body.length() + "\r\n\r\n" + body);

                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            throw new HttpfsException("Address/Port combination not valid");
        }

    }
}
