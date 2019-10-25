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

import static constants.Constants.GET;
import static constants.Constants.HTTP10;

public class HttpServer {

    private HttpServer() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(ServerConfiguration serverConfiguration) throws HttpfsException {
        try {
            // TODO overhaul this exception stuff, it should all be RESPONSES
            SocketAddress bindAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), serverConfiguration.getPort());
            ServerSocket server = new ServerSocket();

            server.bind(bindAddress);

            System.out.println("Httpfs server started at localhost:" + serverConfiguration.getPort());
            System.out.println("Root data directory set to " + serverConfiguration.getDataDirectory());

            // Server runs until terminated
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                // TODO awkward control flow, need to fix
                try {
                    BaseRequest request = RequestParsingService.parseRequest(in);

                    Response response;
                    if (request.getMethod().equalsIgnoreCase(GET)) {
                        response = GetFileService.handleRequest((GetRequest) request, serverConfiguration.getDataDirectory());
                    } else {
                        response = PostFileService.handleRequest((PostRequest) request, serverConfiguration.getDataDirectory());
                    }

                    out.println(response);

                    printDebugging(serverConfiguration, request, response);
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

    private static void printDebugging(ServerConfiguration serverConfiguration, BaseRequest request, Response response) {
        String requestDebugging = (serverConfiguration.isVerbose()) ? request.toString() : request.getSimpleOutput();
        String responseDebugging = (serverConfiguration.isVerbose()) ? response.toString() : response.getSimpleOutput();

        System.out.println("\n--- Request received from client ---");
        System.out.println(requestDebugging);
        System.out.println("\n--- Response sent to client ---");
        System.out.println(responseDebugging);
    }
}
