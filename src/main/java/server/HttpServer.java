package server;

import constants.Status;
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
import static constants.Constants.HTTP10;

public class HttpServer {

    private HttpServer() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start() throws HttpfsException {
        try {
            // TODO overhaul this exception stuff, it should all be RESPONSES
            // TODO Port from parsed data
            // TODO What to do for verbose
            SocketAddress bindAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080);
            ServerSocket server = new ServerSocket();

            server.bind(bindAddress);

            // Server runs until terminated
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                // TODO awkward control flow, need to fix
                //  good candidate for debug message (verbose)
                try {
                    BaseRequest request = RequestParsingService.parseRequest(in);

                    Response response;
                    if (request.getMethod().equalsIgnoreCase(GET)) {
                        response = GetFileService.handleRequest((GetRequest) request);
                    } else {
                        response = PostFileService.handleRequest((PostRequest) request);
                    }

                    out.println(response);
                } catch(HttpfsException e) {
                    out.println(new Response(HTTP10, Status.BAD_REQUEST));
                }

                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            throw new HttpfsException("Address/Port combination not valid");
        }

    }
}
