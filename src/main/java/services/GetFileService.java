package services;

import constants.Status;
import data.GetRequest;
import data.Response;
import exception.HttpfsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static constants.Constants.CONTENT_LENGTH;
import static constants.Constants.CONTENT_TYPE;

public class GetFileService {

    private GetFileService() { }

    public static Response handleRequest(GetRequest getRequest, String dataDirectory) {
        try {
            Path pathToFile = DirectoryService.validateGetPath(dataDirectory, getRequest.getUri());
            StringBuilder body = new StringBuilder();
            String contentType = "text/plain";

            if (Files.isDirectory(pathToFile)) {
                Stream<Path> walk = Files.walk(pathToFile);
                walk.forEach(path -> body.append(path.toString()).append("\n"));
                walk.close();
            } else if (Files.notExists(pathToFile)) {
                return new Response(getRequest.getHttpVersion(), Status.NOT_FOUND);
            } else if (!Files.isReadable(pathToFile)) {
                return new Response(getRequest.getHttpVersion(), Status.FORBIDDEN);
            } else {
                // Valid file that can be read
                String fileContents = new String(Files.readAllBytes(pathToFile));
                body.append(fileContents);
                contentType = Files.probeContentType(pathToFile);
            }

            Map<String, String> headers = new HashMap<>(getRequest.getHeaders());
            headers.put(CONTENT_LENGTH, Integer.toString(body.length()));
            headers.put(CONTENT_TYPE, contentType);

            return new Response(getRequest.getHttpVersion(), Status.OK, headers, body.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(getRequest.getHttpVersion(), Status.INTERNAL_ERROR);
        } catch (HttpfsException e) {
            return new Response(getRequest.getHttpVersion(), Status.BAD_REQUEST);
        }
    }
}
