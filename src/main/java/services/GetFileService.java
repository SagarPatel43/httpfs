package services;

import data.GetRequest;
import data.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static constants.Constants.OK_CODE;
import static constants.Constants.OK_PHRASE;

public class GetFileService {

    private GetFileService() { }

    private static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

    public static Response handleRequest(GetRequest getRequest) {
        // TODO if (is directory) do that listing thing
        // TODO is readable writable
        Path pathToFile = Paths.get(cwd, getRequest.getUri());

        if (Files.isDirectory(pathToFile)) {
            System.err.println("list directory stuff");
        } else {
            if (Files.notExists(pathToFile)) {
                System.err.println("it dont exist");
            } else if (!Files.isReadable(pathToFile)) {
                System.err.println("403 forbidden my guy");
            }

            try {
                String body = new String(Files.readAllBytes(pathToFile));

                return new Response(getRequest.getHttpVersion(), OK_CODE, OK_PHRASE, getRequest.getHeaders(), body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
