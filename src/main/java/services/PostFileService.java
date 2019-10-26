package services;

import constants.Status;
import data.PostRequest;
import data.Response;
import exception.HttpfsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PostFileService {

    private PostFileService() { }

    public static Response handleRequest(PostRequest postRequest, String dataDirectory) {
        try {
            Path pathToFile = DirectoryService.validatePostPath(dataDirectory, postRequest.getUri());
            Files.createDirectories(pathToFile.getParent());

            if (Files.notExists(pathToFile)) {
                Files.createFile(pathToFile);
            }

            if (!Files.isWritable(pathToFile)) {
                return new Response(postRequest.getHttpVersion(), Status.FORBIDDEN);
            }

            Files.write(pathToFile, postRequest.getBody().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(postRequest.getHttpVersion(), Status.INTERNAL_ERROR);
        } catch (HttpfsException e) {
            return new Response(postRequest.getHttpVersion(), Status.BAD_REQUEST);
        }

        return new Response(postRequest.getHttpVersion(), Status.CREATED, postRequest.getHeaders());
    }


}
