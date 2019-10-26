package server;

import constants.Status;
import data.*;
import exception.HttpfsException;
import services.GetFileService;
import services.PostFileService;
import services.RequestParsingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static constants.Constants.*;

public class HttpServer {

    private HttpServer() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(ServerConfiguration serverConfiguration) {
        PrintWriter out = null;
        try {
            SocketAddress bindAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), serverConfiguration.getPort());
            ServerSocket server = new ServerSocket();

            server.bind(bindAddress);

            System.out.println("Httpfs server started at localhost:" + serverConfiguration.getPort());
            System.out.println("Root data directory set to " + serverConfiguration.getDataDirectory());

            // Server runs until terminated
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);

                BaseRequest request = null;
                Response response = null;
                try {
                    request = RequestParsingService.parseRequest(in);
                } catch (HttpfsException e) {
                    e.printStackTrace();
                    response = new Response(HTTP10, Status.BAD_REQUEST);
                }


                if (request != null && request.getMethod().equalsIgnoreCase(GET)) {
                    response = GetFileService.handleRequest((GetRequest) request, serverConfiguration.getDataDirectory());
                } else if (request != null && request.getMethod().equalsIgnoreCase(POST)) {
                    response = PostFileService.handleRequest((PostRequest) request, serverConfiguration.getDataDirectory());
                }

                out.println(response);

                printDebugging(serverConfiguration, request, response);

                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            if (out != null) out.println(new Response(HTTP10, Status.INTERNAL_ERROR));
            e.printStackTrace();
        }
    }

    private static void printDebugging(ServerConfiguration serverConfiguration, BaseRequest request, Response response) {
        if (request != null) {
            String requestDebugging = (serverConfiguration.isVerbose()) ? request.toString() : request.getSimpleOutput();
            System.out.println("\n--- Request received from client ---");
            System.out.println(requestDebugging);
        }
        if (response != null) {
            String responseDebugging = (serverConfiguration.isVerbose()) ? response.toString() : response.getSimpleOutput();
            System.out.println("\n--- Response sent to client ---");
            System.out.println(responseDebugging);
        }
    }
}
