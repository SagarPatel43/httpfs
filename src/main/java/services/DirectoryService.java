package services;

import exception.HttpfsException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DirectoryService {

    static void validatePath(String path) throws HttpfsException {
        Path pathToFile = Paths.get(path);

        if (!pathToFile.isAbsolute()) {
            throw new HttpfsException("Httpfs must be provided an absolute path");
        } else if (!Files.isDirectory(pathToFile)) {
            throw new HttpfsException("Httpfs must be provided a valid directory");
        } else if (!Files.isReadable(pathToFile)) {
            throw new HttpfsException("Insufficient permissions to read file");
        } else if (!Files.isWritable(pathToFile)) {
            throw new HttpfsException("Insufficient permissions to write to file");
        }

        checkPathTraversal(pathToFile);
    }

    static Path validatePostPath(String rootPath, String uri) throws HttpfsException {
        Path pathToFile = Paths.get(rootPath, uri);

        checkPathTraversal(pathToFile);

        if (pathToFile.equals(Paths.get(rootPath))) {
            throw new HttpfsException("No path was specified for POST request");
        }

        return pathToFile;
    }

    static Path validateGetPath(String rootPath, String uri) throws HttpfsException {
        Path pathToFile = Paths.get(rootPath, uri);

        checkPathTraversal(pathToFile);

        return pathToFile;
    }

    private static void checkPathTraversal(Path pathToFile) throws HttpfsException {
        if (!pathToFile.equals(pathToFile.normalize())) {
            throw new HttpfsException("Path traversal is not permitted!");
        }
    }

}
