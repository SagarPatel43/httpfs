package services;

import data.PostRequest;
import data.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static constants.Constants.CREATED_CODE;
import static constants.Constants.CREATED_PHRASE;

public class PostFileService {

    private PostFileService() { }

    private static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

    public static Response handleRequest(PostRequest postRequest) {
        try {
            Path pathToFile = Paths.get(cwd, postRequest.getUri());
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);

            Files.write(pathToFile, postRequest.getBody().getBytes());
        } catch (IOException e) {
            // http response
            e.printStackTrace();
        }

        return new Response(postRequest.getHttpVersion(), CREATED_CODE, CREATED_PHRASE, postRequest.getHeaders());
    }


}
