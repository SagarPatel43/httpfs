package services;

import data.ServerConfiguration;
import exception.HttpfsException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static constants.Constants.CWD;

public class ServerConfigParsingService {

    private ServerConfigParsingService() {
    }

    public static ServerConfiguration parseServerConfiguration(String[] args) throws HttpfsException, IOException {
        boolean verbose;
        int port = 8080;
        String path = CWD;

        OptionParser parser = new OptionParser();

        OptionSpec<Void> verboseSpec = parser.accepts("v");
        OptionSpec<String> portSpec = parser.accepts("p")
                .withRequiredArg();
        OptionSpec<String> directorySpec = parser.accepts("d")
                .withRequiredArg();

        OptionSet options = parser.parse(args);

        verbose = options.has(verboseSpec);

        if (options.has(portSpec)) {
            port = Integer.parseInt(options.valueOf(portSpec));
            if (port < 1024 || port > 65535) {
                throw new HttpfsException("Port must be in range of 1024 and 65535");
            }
            try (Socket ignored = new Socket(InetAddress.getLoopbackAddress(), port)) {
                throw new HttpfsException("Port " + port + " is not available, please try another port");
            } catch (IOException ignored) {
            }
        }


        if (options.has(directorySpec)) {
            String validPath = options.valueOf(directorySpec);
            DirectoryService.validatePath(validPath);
            path = validPath;
        }

        return new ServerConfiguration(verbose, port, path);
    }
}
