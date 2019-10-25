package services;

import constants.Status;
import data.GetRequest;
import data.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GetFileService {

    private GetFileService() { }

    // TODO change this to data directory (currently at root)
    private static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

    public static Response handleRequest(GetRequest getRequest) {
        Path pathToFile = Paths.get(cwd, getRequest.getUri());
        StringBuilder body = new StringBuilder();

        if (Files.isDirectory(pathToFile)) {
            try (Stream<Path> walk = Files.walk(pathToFile)) {
                walk.forEach(path -> body.append(path.toString()).append("\n"));
            } catch (IOException e) {
                // TODO not sure what this indicates as far as server/client goes
                e.printStackTrace();
            }
        } else if (Files.notExists(pathToFile)) {
            return new Response(getRequest.getHttpVersion(), Status.NOT_FOUND, getRequest.getHeaders());
        } else if (!Files.isReadable(pathToFile)) {
            // TODO not sure if this actually works
            return new Response(getRequest.getHttpVersion(), Status.FORBIDDEN, getRequest.getHeaders());
        } else {
            // Valid file that can be read
            try {
                String fileContents = new String(Files.readAllBytes(pathToFile));
                body.append(fileContents);
            } catch (IOException e) {
                // TODO not sure what this indicates as far as server/client goes
                e.printStackTrace();
            }
        }

        return new Response(getRequest.getHttpVersion(), Status.OK, getRequest.getHeaders(), body.toString());
    }
}
