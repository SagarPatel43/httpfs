import data.ServerConfiguration;
import exception.HttpfsException;
import server.HttpServer;
import services.ServerConfigParsingService;

public class httpfs {

    public static void main(String[] args) {
        try {
            ServerConfiguration serverConfiguration = ServerConfigParsingService.parseServerConfiguration(args);
            HttpServer.start(serverConfiguration);
        } catch (HttpfsException e) {
            // Thrown by ServerConfigParsingService (execution terminates)
            e.printStackTrace();
        }
    }
}
