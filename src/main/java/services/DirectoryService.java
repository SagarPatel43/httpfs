package services;

import exception.HttpfsException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class DirectoryService {

    static void validatePath(String path) throws HttpfsException, IOException {
        File file = new File(path);

        if (!file.isAbsolute()) {
            throw new HttpfsException("Httpfs must be provided an absolute path");
        } else if(!file.isDirectory()) {
            throw new HttpfsException("Httpfs must be provided a valid directory");
        } else if (!Files.isReadable(file.toPath())){
            throw new HttpfsException("Insufficient permissions to read file");
        } else if (!Files.isWritable(file.toPath())){
            throw new HttpfsException("Insufficient permissions to write to file");
        }

        String canonicalPath = file.getCanonicalPath();
        String absolutePath = file.getAbsolutePath();

        if (!canonicalPath.equalsIgnoreCase(absolutePath)) {
            throw new HttpfsException("Path traversal is not permitted!");
        }
    }


}
