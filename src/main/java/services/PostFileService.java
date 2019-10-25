package services;

import constants.Status;
import data.PostRequest;
import data.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PostFileService {

    private PostFileService() { }

    // TODO DO WE WANT TO RETURN CONTENT-LENGTH BACK IN RESPONSE?
    public static Response handleRequest(PostRequest postRequest, String dataDirectory) {
        try {
            Path pathToFile = Paths.get(dataDirectory, postRequest.getUri());
            Files.createDirectories(pathToFile.getParent());
            // TODO added this for overwrite
            if (Files.notExists(pathToFile)) {
                Files.createFile(pathToFile);
            }

            Files.write(pathToFile, postRequest.getBody().getBytes());
        } catch (IOException e) {
            // http response
            e.printStackTrace();
        }

        return new Response(postRequest.getHttpVersion(), Status.CREATED, postRequest.getHeaders());
    }


}
